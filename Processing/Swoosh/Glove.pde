class Glove {
  int ax = 0;
  int ay = 0;
  int az = 0;
  int gx = 0;
  int gy = 0;
  int gz = 0;
  int mx = 0;
  int my = 0;
  int mz = 0;
  int flexa = 0;
  int flexb = 0;
  int flexc = 0;
  int flexd = 0;
  int flexe = 0;

  boolean buttona = false;
  boolean buttonb = false;
  boolean buttonc = false;
  boolean buttond = false;
  boolean buttone = false;
  boolean buttonf = false;
  boolean buttong = false;
  boolean buttonh = false;
  
  int packet = 0;
  
  void parseData(String inString) {
    if (inString != null && !("".equals(inString.trim()))) {
      String[] data = inString.trim().split(" ");
      if(data.length != 23) return;
      ax = int(data[0]);
      ay = int(data[1]);
      az = int(data[2]);
      gx = int(data[3]);
      gy = int(data[4]);
      gz = int(data[5]);
      mx = int(data[6]);
      my = int(data[7]);
      mz = int(data[8]);
  
      flexa = int(data[9]);
      flexb = int(data[10]);
      flexc = int(data[11]);
      flexd = int(data[12]);
      flexe = int(data[13]);
    
      buttona = int(data[14]) == 0;
      buttonb = int(data[15]) == 0;
      buttonc = int(data[16]) == 0;
      buttond = int(data[17]) == 0;
      buttone = int(data[18]) == 0;
      buttonf = int(data[19]) == 0;
      buttong = int(data[20]) == 0;
      buttonh = int(data[21]) == 0;
    
      packet = int(data[22]);
    }
  }
}
