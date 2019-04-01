package utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.SubjectTerm;

public class ReadEmail {
	
	private static Folder folder;
	public static PropertyUtility prop=new PropertyUtility();
	public enum EmailFolder {
	    INBOX("INBOX"),
	    SPAM("SPAM"),
		UPDATES("UPDATES"),
		FORUMS("FORUMS");
		  
	    private String text;

	    private EmailFolder(String text){
	      this.text = text;
	    }

	    public String getText() {
	      return text;
	    }
	  }


  /**
   * Uses email.username and email.password properties from the properties file. Reads from Inbox folder of the email application
   * @throws MessagingException
   */
  public ReadEmail() throws MessagingException {
    this(EmailFolder.INBOX);
  }
 
  
  /**
   * Uses username and password in properties file to read from a given folder of the email application
   * @param emailFolder Folder in email application to interact with
   * @throws MessagingException
   */
  public ReadEmail(EmailFolder emailFolder) throws MessagingException {
    this(getEmailUsernameFromProperties(),
        getEmailPasswordFromProperties(),
        getEmailServerFromProperties(),
        getEmailProtocolFromProperties(),
        getEmailPortFromProperties(),
        emailFolder);
  }
  
  
  
 /**
  * Connects to email server with credentials provided to read from a given folder of the email application
  * @param username Email username (e.g. janedoe@email.com)
  * @param password Email password
  * @param server Email server (e.g. smtp.email.com)
 * @param protocol 
  * @param emailFolder Folder in email application to interact with
  */
  public ReadEmail(String username, String password, String server, String protocol, int port,EmailFolder emailFolder) throws MessagingException {
	    Properties props = System.getProperties();
	    try {
	      props.load(new FileInputStream(new File("propertyFiles/config.properties")));
	    } catch(Exception e) {
	      e.printStackTrace();
	      System.exit(-1);
	    }

	   Session session = Session.getInstance(props);
	    Store store = session.getStore("imaps");
	    store.connect(server, username, password);


	    folder = store.getFolder(emailFolder.getText());
	    folder.open(Folder.READ_WRITE);
	  }
	  
  

 
  //************* GET EMAIL PROPERTIES *******************
 
  @SuppressWarnings("static-access")
  public static String getEmailAddressFromProperties(){
    return prop.getProperty("email.address");
  }
 
  @SuppressWarnings("static-access")
  public static String getEmailUsernameFromProperties(){
    return prop.getProperty("email.username");
  }
 
  @SuppressWarnings("static-access")
  public static String getEmailPasswordFromProperties(){
    return prop.getProperty("email.password");
  }
 
  @SuppressWarnings("static-access")
  public static String getEmailProtocolFromProperties(){
    return prop.getProperty("email.protocol");
  }
 
  @SuppressWarnings("static-access")
public static int getEmailPortFromProperties(){
   // return Integer.parseInt(System.getProperty("email.port"));
	  return Integer.parseInt(prop.getProperty("email.port"));
  }
 
  @SuppressWarnings("static-access")
  public static String getEmailServerFromProperties(){
    return prop.getProperty("email.server");
  }



  //************* EMAIL ACTIONS *******************

  public void openEmail(Message message) throws Exception{
    message.getContent();
  }

  public static int getNumberOfMessages() throws MessagingException {
    return folder.getMessageCount();
  }

  public int getNumberOfUnreadMessages()throws MessagingException {
    return folder.getUnreadMessageCount();
  }
  
  

  /**
   * Gets a message by its position in the folder. The earliest message is indexed at 1.
   */
  public Message getMessageByIndex(int index) throws MessagingException {
    return folder.getMessage(index);
  }

  public Message getLatestMessage() throws MessagingException{
    return getMessageByIndex(getNumberOfMessages());
  }

  /**
   * Gets all messages within the folder
   */
  public Message[] getAllMessages() throws MessagingException {
    return folder.getMessages();
  }

  /**
   * @param maxToGet maximum number of messages to get, starting from the latest. For example, enter 100 to get the last 100 messages received.
   */
  public Message[] getMessages(int maxToGet) throws MessagingException {
    Map<String, Integer> indices = getStartAndEndIndices(maxToGet);
    return folder.getMessages(indices.get("startIndex"), indices.get("endIndex"));
  }

  /**
   * Searches for messages with a specific subject
   * @param subject Subject to search messages for
   * @param unreadOnly Indicate whether to only return matched messages that are unread
   * @param maxToSearch maximum number of messages to search, starting from the latest. For example, enter 100 to search through the last 100 messages.
   */
  public static Message[] getMessagesBySubject(String subject, boolean unreadOnly, int maxToSearch) throws Exception{
    Map<String, Integer> indices = getStartAndEndIndices(maxToSearch);
    System.out.println("INSIDE GET MESSAGE BY SUBJECT METHOD");
    Message messages[] = folder.search(
        new SubjectTerm(subject),
        folder.getMessages(indices.get("startIndex"), indices.get("endIndex")));

    if(unreadOnly){
      List<Message> unreadMessages = new ArrayList<Message>();
      for (Message message : messages) {
        if(isMessageUnread(message)) {
          unreadMessages.add(message);
        }
      }
      messages = unreadMessages.toArray(new Message[]{});
    }

    return messages;
  }

  /**
   * Returns HTML of the email's content
   */
  public String getMessageContent(Message message) throws Exception {
    StringBuffer buffer = new StringBuffer();
    BufferedReader reader = new BufferedReader(new InputStreamReader(message.getInputStream()));
    String line;
    while ((line = reader.readLine()) != null) {
      buffer.append(line);
    }
    return buffer.toString();
  }

  /**
   * Returns all urls from an email message with the linkText specified
   */
  public List<String> getUrlsFromMessage(Message message, String linkText) throws Exception{
    String html = getMessageContent(message);
    List<String> allMatches = new ArrayList<String>();
    Matcher matcher = Pattern.compile("(<a [^>]+>)"+linkText+"</a>").matcher(html);
    while (matcher.find()) {
      String aTag = matcher.group(1);
      allMatches.add(aTag.substring(aTag.indexOf("http"), aTag.indexOf("\">")));
    }
    return allMatches;
  }

  private static Map<String, Integer> getStartAndEndIndices(int max) throws MessagingException {
    int endIndex = getNumberOfMessages();
    int startIndex = endIndex - max;

    //In event that maxToGet is greater than number of messages that exist
    if(startIndex < 1){
      startIndex = 1;
    }

    Map<String, Integer> indices = new HashMap<String, Integer>();
    indices.put("startIndex", startIndex);
    indices.put("endIndex", endIndex);

    return indices;
  }
  /**
   * Searches an email message for a specific string
   */
  public boolean isTextInMessage(Message message, String text) throws Exception {
    String content = getMessageContent(message);
 
    //Some Strings within the email have whitespace and some have break coding. Need to be the same.
    content = content.replace("&nbsp;", " ");
    return content.contains(text);
  }
 
  public boolean isMessageInFolder(String subject, boolean unreadOnly) throws Exception {
    int messagesFound = getMessagesBySubject(subject, unreadOnly, getNumberOfMessages()).length;
    return messagesFound > 0;
  }
 
  public static boolean isMessageUnread(Message message) throws Exception {
    return !message.isSet(Flags.Flag.SEEN);
  }


/**
 * Gets text from the end of a line. 
 * In this example, the subject of the email is 'Authorization Code'
 * And the line to get the text from begins with 'Authorization code:'
 * Change these items to whatever you need for your email. This is only an example.
*/ 
 public static String getAccessCode() throws Exception {
    Message[] emails = getMessagesBySubject("SCU Registration", true, 5);
    Message email=emails[emails.length-1];
    BufferedReader reader = new BufferedReader(new InputStreamReader(email.getInputStream()));
    String line;
    String prefix = "access code";
    String suffix =". ";
    while ((line = reader.readLine()) != null) {
      if(line.contains(prefix)) {
    	  return line.substring(line.indexOf(prefix) + prefix.length(), line.lastIndexOf(suffix)).trim();
      }
    }
    return null;
  }
  
  public static String getAccessCodeReceivedInPhone() throws Exception {
	    Message[] emails = getMessagesBySubject("New text message from 37083", true, 5);
	    Message email=emails[emails.length-1];
	    BufferedReader reader = new BufferedReader(new InputStreamReader(email.getInputStream()));
	 
	    String line;
	    String prefix = "access code";
	    String suffix =". ";
	    while ((line = reader.readLine()) != null) {
	      if(line.contains(prefix)) {
	    	  return line.substring(line.indexOf(prefix) + prefix.length(), line.lastIndexOf(suffix)).trim(); 
	      }
	    }
	    return null;
	  }
  
  public static String getAccessCodeForForgotPassword() throws Exception {
	    Message[] emails = getMessagesBySubject("Your verification code", true, 5);
	    Message email=emails[emails.length-1];
	    BufferedReader reader = new BufferedReader(new InputStreamReader(email.getInputStream()));
	 
	    String line;
	    String prefix = "reset code is";
	    String suffix ="";
	    while ((line = reader.readLine()) != null) {
	      if(line.contains(prefix)) {
	    	  return line.substring(line.indexOf(prefix) + prefix.length(), line.lastIndexOf(suffix)).trim();
	      }
	    }
	    return null;
	  }
	  
 
 public PasswordAuthentication getPasswordAuthentication() {
	 String username = PropertyUtility.getProperty("email.username");
	 String password = PropertyUtility.getProperty("email.password");
	    if ((username != null) && (username.length() > 0) && (password != null) 
	      && (password.length   () > 0)) {

	        return new PasswordAuthentication(username, password);
	    }

	    return null;
 }
 
 /**
  * Deletes all e-mail messages whose subject field contain
  * a string specified by 'subjectToDelete'
  * @param host
  * @param port
  * @param userName
  * @param password
  * @param subjectToDelete delete if the message's subject contains this value.
  */
 public void deleteMessages(String host, String port,
         String userName, String password, String subjectToDelete) {
     Properties properties = new Properties();

     // server setting
     properties.put("mail.imap.host", host);
     properties.put("mail.imap.port", port);

     // SSL setting
     properties.setProperty("mail.imap.socketFactory.class",
             "javax.net.ssl.SSLSocketFactory");
     properties.setProperty("mail.imap.socketFactory.fallback", "false");
     properties.setProperty("mail.imap.socketFactory.port",
             String.valueOf(port));

     Session session = Session.getDefaultInstance(properties);

     try {
         // connects to the message store
         Store store = session.getStore("imap");
         store.connect(userName, password);

         // opens the inbox folder
         Folder folderInbox = store.getFolder("INBOX");
         folderInbox.open(Folder.READ_WRITE);

         // fetches new messages from server
         Message[] arrayMessages = folderInbox.getMessages();

         for (int i = 0; i < arrayMessages.length; i++) {
             Message message = arrayMessages[i];
             String subject = message.getSubject();
             if (subject.contains(subjectToDelete)) {
                 message.setFlag(Flags.Flag.DELETED, true);
             }

         }

         // expunges the folder to remove messages which are marked deleted
         boolean expunge = true;
         folderInbox.close(expunge);

         // another way:
         //folderInbox.expunge();
         //folderInbox.close(false);

         // disconnect
         store.close();
     } catch (NoSuchProviderException ex) {
         Log.addMessage("No provider.");
         ex.printStackTrace();
     } catch (MessagingException ex) {
    	 Log.addMessage("Could not connect to the message store.");
         ex.printStackTrace();
     }
 }

 
 public String getActivationLink(String subject) throws Exception {
	 String actualString = "";
	 	String aTag = "";
	    Message[] emails = getMessagesBySubject(subject, true, 5);
	    Message email=emails[emails.length-1];
	    String html = getMessageContent(email);
	    Matcher matcher = Pattern.compile("href=([\\s\\S]*?)>").matcher(html);
	    while (matcher.find()) {
	    	System.out.println("We have a match !!");
	       aTag = matcher.group();
	       if(!aTag.equals("")) {
	    	   break;
	       }
	      }
	    
	  return aTag.substring(6, aTag.length()-2);
	}
 
 
}
  

