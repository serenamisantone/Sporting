package sporting.business.impl.file;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.google.zxing.EncodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import sporting.business.BusinessException;
import sporting.business.CapienzaEsauritaException;
import sporting.business.LezioneService;
import sporting.business.PrenotazioneService;
import sporting.business.SalaService;
import sporting.business.UtenteService;
import sporting.domain.Cliente;
import sporting.domain.Prenotazione;

public class FilePrenotazioneServiceImpl implements PrenotazioneService {
	private String prenotazioniFileName;
	private LezioneService lezioneService;
	private UtenteService utenteService;
	private SalaService salaService;

	public FilePrenotazioneServiceImpl(String filename, LezioneService lezioneService, UtenteService utenteService,
			SalaService salaService) {
		prenotazioniFileName = filename;
		this.lezioneService = lezioneService;
		this.utenteService = utenteService;
		this.salaService = salaService;
	}

	@Override
	public void addPrenotazione(Prenotazione prenotazione) throws BusinessException, CapienzaEsauritaException {
		try {
			if (prenotazione.getLezione() != null) {

				lezioneService.decreaseCapienzaLezione(prenotazione.getLezione());

			} else {
				if (checkCapienzaSala(prenotazione))
					throw new CapienzaEsauritaException();
			}

			FileData fileData = Utility.readAllRows(prenotazioniFileName);
			try (PrintWriter writer = new PrintWriter(new File(prenotazioniFileName))) {
				long contatore = fileData.getContatore();
				writer.println((contatore + 1));
				for (String[] righe : fileData.getRighe()) {
					writer.println(String.join(Utility.SEPARATORE_COLONNA, righe));
				}
				StringBuilder row = new StringBuilder();
				row.append(contatore);
				prenotazione.setId((int) contatore);
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(prenotazione.getCliente().getId());
				row.append(Utility.SEPARATORE_COLONNA);
				if (prenotazione.getLezione() != null) {
					row.append("lezione");
					row.append(Utility.SEPARATORE_COLONNA);
					row.append(prenotazione.getLezione().getId());

				} else {
					row.append("sala");
					row.append(Utility.SEPARATORE_COLONNA);
					row.append(prenotazione.getSala().getId());
					row.append(Utility.SEPARATORE_COLONNA);
					row.append(prenotazione.getData());
					row.append(Utility.SEPARATORE_COLONNA);
					row.append(prenotazione.getOrarioInizio());

				}
				row.append(Utility.SEPARATORE_COLONNA);
				String path = "qrcode" + prenotazione.getId().toString();
				row.append(path);
				prenotazione.setQrcode(path);
				writer.println(row.toString());
			}
			generateQrcode(prenotazione);
			//sendEmail(prenotazione);
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		} catch (NotFoundException e) {
			e.printStackTrace();
		} catch (WriterException e) {

			e.printStackTrace();
		}

	}

	@Override
	public boolean checkCapienzaSala(Prenotazione prenotazione) throws BusinessException {
		int cont = 50;
		try {
			FileData fileData = Utility.readAllRows(prenotazioniFileName);
			// 1,1,sala,1,data,orario
			for (String[] colonne : fileData.getRighe()) {
				if (colonne[2].equals("sala") && Integer.parseInt(colonne[3]) == prenotazione.getSala().getId()
						&& LocalDate.parse(colonne[4]).equals(prenotazione.getData())
						&& LocalTime.parse(colonne[5]).equals(prenotazione.getOrarioInizio())) {
					cont--;
				}
				if (cont <= 0)
					return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
		return false;
	}

	@Override
	public void generateQrcode(Prenotazione prenotazione) throws WriterException, IOException, NotFoundException {
		// I dati che conterranno i qrcodes
		String data = prenotazione.getId().toString();

		// il path dove verra salvato il qrcode
		String path = "qrcodes/" + prenotazione.getQrcode() + ".png";


		String charset = "UTF-8";

		Map<EncodeHintType, ErrorCorrectionLevel> hashMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();

		hashMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

		// crea il qrcode e salva nel path specificato come png
		
		Qrcode.createQR(data, path, charset, hashMap, 200, 200);
		System.out.println("QR Code Generated!!! ");

	}

	public void sendEmail(Prenotazione prenotazione) {
		// email del cliente a cui inviare la mail
		String to = prenotazione.getCliente().getEmail();

		// email del mittente
		String from = "";

		String host = "smtp.gmail.com";

		// Get system properties
		Properties properties = System.getProperties();

		// Setup mail server
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.auth", "true");

		// Get the Session object.// and pass username and password
		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

			protected PasswordAuthentication getPasswordAuthentication() {

				return new PasswordAuthentication("", "");

			}
		});
		try {
			// Create a default MimeMessage object.
			Message message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

			// Set Subject: header field
			message.setSubject("Prenotazione Sporting");

			// This mail has 2 part, the BODY and the embedded image
			MimeMultipart multipart = new MimeMultipart("related");

			// first part (the html)
			BodyPart messageBodyPart = new MimeBodyPart();
			String htmlText = "<H1>QRcode prenotazione</H1> Utilizza questo QR-code per effettuare il checkin ed il checkout. <br>"
					+ "Prenotazione per il giorno:"
					+ prenotazione.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " alle ore: "
					+ prenotazione.getOrarioInizio() + "<br><img src=\"cid:image\">";

			messageBodyPart.setContent(htmlText, "text/html");
			// add it
			multipart.addBodyPart(messageBodyPart);

			// second part (the image)
			messageBodyPart = new MimeBodyPart();
			DataSource fds = new FileDataSource("qrcodes\\" + prenotazione.getQrcode() + ".png");

			messageBodyPart.setDataHandler(new DataHandler(fds));
			messageBodyPart.setHeader("Content-ID", "<image>");

			// add image to the multipart
			multipart.addBodyPart(messageBodyPart);

			// put everything together
			message.setContent(multipart);
			// Send message
			Transport.send(message);

			System.out.println("Sent message successfully....");

		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}

	@Override
	public List<Prenotazione> findAllPrenotazioni(Cliente cliente) throws BusinessException {
		List<Prenotazione> result = new ArrayList<>();
		try {
			FileData fileData = Utility.readAllRows(prenotazioniFileName);
			for (String[] colonne : fileData.getRighe()) {
				// 1,1,sala,1,2022-02-18,16:00,qrcode1
				if (cliente.getId() == Integer.parseInt(colonne[1])) {
					Prenotazione prenotazione = new Prenotazione();
					prenotazione.setId(Integer.parseInt(colonne[0]));
					prenotazione.setCliente((Cliente) utenteService.findUtenteById(Integer.parseInt(colonne[1])));
					if (colonne[2].equals("sala")) {
						prenotazione.setSala(salaService.findSalaById(Integer.parseInt(colonne[3])));
						prenotazione.setData(LocalDate.parse(colonne[4]));
						;
						prenotazione.setOrarioInizio(LocalTime.parse(colonne[5]));
						prenotazione.setQrcode(colonne[6]);
					} else {
						prenotazione.setLezione(lezioneService.findLezioneById(Integer.parseInt(colonne[3])));
						prenotazione.setData(prenotazione.getLezione().getData());
						prenotazione.setOrarioInizio(prenotazione.getLezione().getOrarioInizio());
						prenotazione.setQrcode(colonne[4]);
					}

					result.add(prenotazione);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
		return result;
	}

	@Override
	public void CancellaPrenotazione(Prenotazione prenotazione, Cliente cliente) throws BusinessException {
		try {
			FileData fileData = Utility.readAllRows(prenotazioniFileName);
			try (PrintWriter writer = new PrintWriter(new File(prenotazioniFileName))) {
				writer.println(fileData.getContatore());
				for (String[] colonne : fileData.getRighe()) {
					if (colonne[0].equals(String.valueOf(prenotazione.getId()))
							&& cliente.getId() == Integer.parseInt(colonne[1])) {
						// do nothing
					} else {

						writer.println(String.join(Utility.SEPARATORE_COLONNA, colonne));
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}

	}

}
