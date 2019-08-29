package pack;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;


public class TxtToSpch {

	private String name;
	
	private Voice voice;
	
	
	public TxtToSpch(String name) {
		//super();
		this.name = name;
		this.voice = VoiceManager.getInstance().getVoice(this.name);
		this.voice.allocate();
	}

	public void say(String ch){
		
		this.voice.speak(ch);
	}
	
	public void sayMultiple(String [] sayMePls){
		
		for (int i = 0; i < sayMePls.length; i++) {
			
			this.say(sayMePls[i]);
		}
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		TxtToSpch v = new TxtToSpch("kevin16");
		
		String[] sayMe = new String[] {
				"Hello badis! I am ASUS!",
				"I am your computer!",
				"If you need any thing just tell me"
		};
		
		v.sayMultiple(sayMe);

	}

}
