package br.com.jao.mail;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;

import javax.mail.FetchProfile;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Header;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.FlagTerm;
import javax.mail.search.SearchTerm;

public class Email {

	public void showAllMails(Folder inbox) {
		try {
			System.out.println("Email.showAllMails()");
			Message msg[] = inbox.getMessages();
			
			System.out.println(msg.length);
			
			LocalDate date = LocalDate.of(2018, Month.MAY, 2);
			DateTerm dateTerm = new DateTerm().withSendDateBiggerThen(date);
			
			FetchProfile fp = new FetchProfile();
			fp.add(FetchProfile.Item.ENVELOPE); 
			fp.add(FetchProfile.Item.FLAGS); 
			fp.add("X-Mailer"); 
			
			Message[] messagesInCurBatch = inbox.search(dateTerm);
			int totalInFolder = messagesInCurBatch.length;
			inbox.fetch(msg, fp);
			
			System.out.println("MAILS: " + totalInFolder);
			/*for (Message message : msg) {
				try {
					System.out.println("DATE: " + message.getSentDate().toString());
					System.out.println("FROM: " + message.getFrom()[0].toString());
					System.out.println("SUBJECT: " + message.getSubject().toString());
					System.out.println("CONTENT: " + message.getContent().toString());
					System.out.println("------------------------------------------");
				} catch (Exception e) {
					System.out.println("No Information");
				}
			}*/
		} catch (MessagingException e) {
			System.out.println(e.toString());
		}
	}

	public void busca() {
		
	}
	
		
	public void showUnreadMails(Folder inbox) {
		try {
			FlagTerm ft = new FlagTerm(new Flags(Flags.Flag.SEEN), false);
			Message msg[] = inbox.search(ft);
			System.out.println("MAILS: " + msg.length);
			for (Message message : msg) {
				try {
					/*System.out.println("DATE: " + message.getSentDate().toString());
					System.out.println("FROM: " + message.getFrom()[0].toString());
					System.out.println("SUBJECT: " + message.getSubject().toString());
					System.out.println("CONTENT: " + message.getContent().toString());
					System.out.println("-------------------------------------------");*/
					
					Enumeration<?> enumeration = message.getAllHeaders();
					while (enumeration.hasMoreElements()) {
						Header header =  (Header) enumeration.nextElement();
						System.out.println(header.getName() + " => " + header.getValue());
					}
					
					
				} catch (Exception e) {
					System.out.println("No Information");
				}
			}
		} catch (MessagingException e) {
			System.out.println(e.toString());
		}
	}
	
	public void teste() {
		Properties props = System.getProperties();
		props.setProperty("mail.store.protocol", "imaps");
		try {
			Session session = Session.getDefaultInstance(props, null);
			Store store = session.getStore("imaps");

			// IMAP host for gmail.
			// Replace with the valid username of your Email ID.
			// Replace with a valid password of your Email ID.

			// store.connect("imap.gmail.com", "", "");

			// IMAP host for yahoo.
			String chave = "tecdqlyrthliugws";
			store.connect("imap.mail.yahoo.com", "jaoleite@yahoo.com.br", chave);

			System.out.println(store.isConnected());

			Folder inbox = store.getFolder("Itaú");
			inbox.open(Folder.READ_ONLY);

			char answer = 'A';
			if (answer == 'A' || answer == 'a') {
				showAllMails(inbox);
			} else if (answer == 'U' || answer == 'u') {
				showUnreadMails(inbox);
			}

			store.close();

		} catch (NoSuchProviderException e) {
			System.out.println(e.toString());
			System.exit(1);
		} catch (MessagingException e) {
			System.out.println(e.toString());
			System.exit(2);
		}

	}

	public static void main(String args[]) {
		new Email().teste();
	}
	
	class DateTerm extends SearchTerm {

		private static final long serialVersionUID = 1L;
		private Message message;
		private LocalDate date;
		private boolean biggerThen = false;
		private boolean lessThen = false;
		private boolean equalTo = false;
		
		public DateTerm() {
			this.equalTo = true;
			this.date = LocalDate.now();
		}
		
		public DateTerm withSendDateBiggerThen(LocalDate date) {
			biggerThen = true;
			this.date = date;
			return this;
		}
		
		public DateTerm withSendDateLessThen(LocalDate date) {
			this.lessThen = true;
			this.date = date;
			return this;
		}
		
		public DateTerm withSentDateEqualTo(LocalDate date) {
			this.equalTo = true;
			this.date = date;
			return this;
		}

		@Override
		public boolean match(Message msg) {
			this.message = msg;
			return math();
		}
		
		private boolean math() {
			LocalDate sentDate = getSentDate();
			// LocalDate agora = LocalDate.of(2018, Month.MAY, 2);
			System.out.println(sentDate);
			if(sentDate != null) {
				if(biggerThen) {
					return sentDate.isAfter(date);
				} else if(lessThen){
					return sentDate.isBefore(date);
				} else if(equalTo) {
					return sentDate.isEqual(date);
				}
			}
			return false;
		}
		
		private LocalDate getSentDate() {
			try {
				Date sentDate = message.getSentDate();
				LocalDate date = sentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				date = LocalDate.of(date.getYear(), date.getMonth(), date.getDayOfMonth());
				return date;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	}

}
