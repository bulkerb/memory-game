//curl getting image
    String command = "curl -X GET https://emojiapi.dev/api/v1/" + unit + "/512.png --output thisemoji.png";
    ProcessBuilder im = new ProcessBuilder(command.split(" "));
    im.directory(new File("memory-game/java/"));
    Process process;
    try {
      process = im.start();
      InputStream inputstream = process.getInputStream();
      int exitCode=process.exitValue();
      process.destroy();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    //var question = getUrlContents("https://emojiapi.dev/api/v1/slightly_smiling_face/512.png");
    //System.out.println(question);