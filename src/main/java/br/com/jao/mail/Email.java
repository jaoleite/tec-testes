package br.com.jao.mail;

import java.util.Enumeration;
import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Header;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.FlagTerm;

public class Email {

	static void showAllMails(Folder inbox) {
		try {
			Message msg[] = inbox.getMessages();
			System.out.println("MAILS: " + msg.length);
			for (Message message : msg) {
				try {
					System.out.println("DATE: " + message.getSentDate().toString());
					System.out.println("FROM: " + message.getFrom()[0].toString());
					System.out.println("SUBJECT: " + message.getSubject().toString());
					System.out.println("CONTENT: " + message.getContent().toString());
					System.out.println("------------------------------------------");
				} catch (Exception e) {
					System.out.println("No Information");
				}
			}
		} catch (MessagingException e) {
			System.out.println(e.toString());
		}
	}

	static void showUnreadMails(Folder inbox) {
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

	public static void main(String args[]) {
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

			char answer = 'U';
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

}
