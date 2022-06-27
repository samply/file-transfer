package de.samply.filetransferclient;

public class FileTransferConst {

  public static final String API_KEY_HEADER = "apiKey";
  public final static String TRANSFER_URL = "/transfer";
  public final static String INFO_URL = "/info";
  public final static String TRANSFER_FILE_PARAMETER = "file";
  public final static String CLIENT_API_KEY = "${CLIENT_API_KEY}";
  public final static String TRANSFER_FILES_CRON_EXPR = "${TRANSFER_FILES_CRON_EXPR:#{'-'}}";
  public final static String TRANSFER_DIRECTORY = "${TRANSFER_FILES_DIRECTORY:#{'.'}}";
  public final static String TARGET_BRIDGEHEAD_URL = "${TARGET_BRIDGEHEAD_URL:#{null}}";
  public final static String TARGET_BRIDGEHEAD_APIKEY = "${TARGET_BRIDGEHEAD_APIKEY:#{null}}";

}
