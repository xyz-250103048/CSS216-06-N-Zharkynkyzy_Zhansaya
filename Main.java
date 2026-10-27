interface Image {
    void show();
}

class RealImage implements Image {
    String fileName;

    RealImage(String fileName) {
        this.fileName = fileName;
    }

    public void show() {
        System.out.println("Displaying " + fileName + " on screen!");
    }
}

class ProxyImage implements Image {
    String fileName;
    String userRole;
    RealImage image;

    ProxyImage(String fileName, String userRole) {
        this.fileName = fileName;
        this.userRole = userRole;
    }

    public void show() {
        if (!userRole.equals("ADMIN")) {
            System.out.println("[ACCESS DENIED] You lack permission to view: " + fileName);
            return;
        }

        if (image == null) {
            System.out.println("--> [SLOW] Reading " + fileName + " from hard drive...");
            image = new RealImage(fileName);
        }

        image.show();
    }
}

interface Video {
    void play();
}

class RealVideo implements Video {
    String fileName;

    RealVideo(String fileName) {
        this.fileName = fileName;
    }

    public void play() {
        System.out.println("Playing video: " + fileName);
    }
}

class ProxyVideo implements Video {
    String fileName;
    RealVideo video;
    int viewCount = 0;

    ProxyVideo(String fileName) {
        this.fileName = fileName;
    }

    public void play() {
        viewCount++;
        System.out.println("--> [AUDIT] Video has been played " + viewCount + " time(s).");

        if (video == null) {
            System.out.println("--> [DOWNLOADING] Connecting to server & buffering 4K video: " + fileName);
            video = new RealVideo(fileName);
        }

        video.play();
    }
}

interface Internet {
    void connectTo(String site);
}

class RealInternet implements Internet {
    public void connectTo(String site) {
        System.out.println("Connected to site: " + site);
    }
}

class SchoolProxyInternet implements Internet {
    RealInternet internet;

    public void connectTo(String site) {
        if (site.equals("gaming.com") || site.equals("socialmedia.com")) {
            System.out.println("[BLOCKED BY FIREWALL] Cannot access: " + site);
            return;
        }

        if (internet == null) {
            internet = new RealInternet();
        }

        internet.connectTo(site);
    }
}

public class Main {
    public static void main(String[] args) {
        Image guest = new ProxyImage("salary_sheet.png", "GUEST");
        Image admin = new ProxyImage("salary_sheet.png", "ADMIN");

        guest.show();
        admin.show();

        Video video = new ProxyVideo("Cat_Memes.mp4");
        video.play();
        video.play();
        video.play();

        Internet internet = new SchoolProxyInternet();
        internet.connectTo("wikipedia.org");
        internet.connectTo("gaming.com");
    }
}