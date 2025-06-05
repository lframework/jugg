package com.lframework.starter.bpm.transfers;

public class FlowCuInstanceTransfer {

  private static final ThreadLocal<String> TITLE_TRANS = new ThreadLocal<>();

  public static void setTitle(String title) {
    TITLE_TRANS.set(title);
  }

  public static String getTitle() {
    return TITLE_TRANS.get();
  }

  public static void clearTitle() {
    TITLE_TRANS.remove();
  }
}
