package basic1;
//Interface
interface ChatBuilder {
 void addMessage(String msg);
 String getChat();
}
class BasicChat implements ChatBuilder {
    private String chat = "";

    public void addMessage(String msg) {
        chat = chat + msg + " | ";
    }

    public String getChat() {
        return chat;
    }
}
class FastChat implements ChatBuilder {
    private StringBuilder chat = new StringBuilder();

    public void addMessage(String msg) {
        chat.append(msg).append(" | ");
    }

    public String getChat() {
        return chat.toString();
    }
}
class SafeChat implements ChatBuilder {
    private StringBuffer chat = new StringBuffer();

    public void addMessage(String msg) {
        chat.append(msg).append(" | ");
    }

    public String getChat() {
        return chat.toString();
    }
}
class Main3 {
    public static void main(String[] args) {

        ChatBuilder basic = new BasicChat();
        ChatBuilder fast = new FastChat();
        ChatBuilder safe = new SafeChat();

        // Add messages
        for (int i = 1; i <= 5; i++) {
            basic.addMessage("Msg" + i);
            fast.addMessage("Msg" + i);
            safe.addMessage("Msg" + i);
        }

        // Output
        System.out.println("String (Basic): " + basic.getChat());
        System.out.println("StringBuilder (Fast): " + fast.getChat());
        System.out.println("StringBuffer (Safe): " + safe.getChat());
    }
}