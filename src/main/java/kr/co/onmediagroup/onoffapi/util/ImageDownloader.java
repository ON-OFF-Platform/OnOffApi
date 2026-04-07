package kr.co.onmediagroup.onoffapi.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageDownloader {

  /**
   * UR`L에서 이미지를 다운로드하고 로컬에 저장
   * @param imageUrl 다운로드할 이미지 URL
   * @param fileName 저장할 파일 이름 (null이면 자동 생성)
   * @return 저장된 파일의 절대 경로
   * @throws IOException 이미지 저장 실패 시 예외 발생
   */
  public static String downloadImageFromURL(String imageUrl, String fileName) throws IOException {
    String saveDir = System.getProperty("user.home") + "/Downloads/tiktok_images/";
    File directory = new File(saveDir);
    if (!directory.exists()) directory.mkdirs();

    if (fileName == null || fileName.isEmpty()) {
      String extension = imageUrl.contains(".") ?
        imageUrl.substring(imageUrl.lastIndexOf(".") + 1) : "jpg";
      fileName = "image_" + System.currentTimeMillis() + "." + extension;
    }

    String savePath = saveDir + fileName;
    URL url = new URL(imageUrl);

    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("GET");
    conn.setConnectTimeout(10000);
    conn.setReadTimeout(10000);
    // TikTok은 User-Agent가 없으면 차단하는 경우 있음
    conn.setRequestProperty("User-Agent", "Mozilla/5.0");
    conn.setInstanceFollowRedirects(true); // 리다이렉트 허용

    int responseCode = conn.getResponseCode();
    if (responseCode != HttpURLConnection.HTTP_OK) {
      throw new IOException("HTTP 응답 코드: " + responseCode);
    }

    try (InputStream in = conn.getInputStream();
         FileOutputStream out = new FileOutputStream(savePath)) {
      byte[] buffer = new byte[4096];
      int bytesRead;
      while ((bytesRead = in.read(buffer)) != -1) {
        out.write(buffer, 0, bytesRead);
      }
    }

    conn.disconnect();
    System.out.println("이미지 저장 완료: " + savePath);
    return savePath;
  }
}
