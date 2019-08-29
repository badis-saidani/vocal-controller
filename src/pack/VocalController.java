package pack;

/*
 * Copyright 1999-2004 Carnegie Mellon University.
 * Portions Copyright 2004 Sun Microsystems, Inc.
 * Portions Copyright 2004 Mitsubishi Electric Research Laboratories.
 * All Rights Reserved.  Use is subject to license terms.
 *
 * See the file "license.terms" for information on usage and
 * redistribution of this file, and for a DISCLAIMER OF ALL
 * WARRANTIES.
 *
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;

import edu.cmu.sphinx.frontend.util.Microphone;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.util.props.ConfigurationManager;
import javax.swing.border.LineBorder;

public class VocalController {

	static String resultText;
	static String answerToStr;
	static String textToSpeech;
	static Process pro;
	static Random ran = new Random();
    public static void main(String[] args) throws InstantiationException {
  
    	
    	
//-----------------------------| GUI COMPONENTS |-----------------------------------------//	
    	/* Main Container */
    	JFrame SpeechFrame=new JFrame("Vocal Controller");
    	SpeechFrame.setSize(480,530);
    	SpeechFrame.setLocationRelativeTo(null);									
		SpeechFrame.setResizable(false); 
		SpeechFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpeechFrame.getContentPane().setLayout(null);
		JLabel mainBackgoundImage=new JLabel ("");								
		mainBackgoundImage.setBounds(0, 0, 474, 501);
		SpeechFrame.getContentPane().add(mainBackgoundImage);										
		mainBackgoundImage.setIcon(new ImageIcon(VocalController.class.getResource("/image/mim.jpg"))); 
		SpeechFrame.setVisible(true);
		mainBackgoundImage.setOpaque(false);
		
		/* How to use Button */
		JButton btnHowToUse=new JButton(new ImageIcon(VocalController.class.getResource("/image/how.jpg")));
		btnHowToUse.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		btnHowToUse.setSelectedIcon(new ImageIcon(VocalController.class.getResource("/image/how.jpg")));
		mainBackgoundImage.add(btnHowToUse);
		btnHowToUse.setRolloverIcon(new ImageIcon ("data"+File.separator+File.separator+"help_actv.data"));
		btnHowToUse.setBounds(20,20,80,80);
		btnHowToUse.setToolTipText("How To Use");
		
		// Action Listener for How to use Button
		btnHowToUse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			   JOptionPane.showMessageDialog(null, "<html> <h3> HOW TO USE </h3> Start the application and u are ready to go<br/> "
			   		+ "Use commands like: "
			   		+ "<br><i><ul><li>Hey Computer</li>"
			   		+ "<li> Do you have a name?</li>"
			   		+ "<li> Open *Application Name*</li>"
			   		+ "<li> Make me a coffee</li> "
			   		+ "</ul>This Application is powered by  <br/> "
			   		+ "+++++  <b>Badis Saidani</b> .<br/> </html>");
			       }
			   });
		
		/* Speech Panel or Output window */
		
		//Pane to display users qryDisps
				JTextArea userSpeechDisp=new JTextArea("");
				mainBackgoundImage.add(userSpeechDisp);
				//userSpeechDisp.setLineWrap(true);
				//userSpeechDisp.setWrapStyleWord(true);
				userSpeechDisp.setBounds(48,290,390,65);
				userSpeechDisp.setForeground(new Color(241, 144, 0));
				userSpeechDisp.setFont(new Font("Cambria" , Font.BOLD, 24));
				Border border = BorderFactory.createLineBorder(new Color(0, 84, 186));
				userSpeechDisp.setBorder(BorderFactory.createCompoundBorder(border, 
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));
				userSpeechDisp.setEditable(false);
				
			//Pane to display Solution or answers
				JTextArea slnDisp=new JTextArea("");
				mainBackgoundImage.add(slnDisp);
				slnDisp.setLineWrap(true);
				slnDisp.setWrapStyleWord(true);
				slnDisp.setForeground(new Color(241, 144, 0));
				slnDisp.setFont(new Font("Cambria" , Font.BOLD, 24));
				slnDisp.setBounds(48,370,390,65);
				slnDisp.setBorder(BorderFactory.createCompoundBorder(border, 
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));
				slnDisp.setEditable(false);
				
				// Listening Notification
				JLabel listening=new JLabel("");
				listening.setForeground(Color.RED);
				listening.setFont(new Font("Cambria" , Font.BOLD, 24));
				mainBackgoundImage.add(listening);
				listening.setBounds(30,200,290,30);
				listening.setText("Loading");
				
							
//--------------------------------| SPEECH RECOGNITION COMPONENTS |-----------------------------------------//	
		
//Loading Grammars and xml config files
	ConfigurationManager cm;
if (args.length > 0) {
		cm = new ConfigurationManager(args[0]);
} else {
		cm = new ConfigurationManager(VocalController.class.getResource("VocalController.config.xml"));
		}

	Recognizer recognizer = (Recognizer) cm.lookup("recognizer");
	recognizer.allocate();
	listening.setText("Listening");
// start the microphone or exit if not
		        Microphone microphone = (Microphone) cm.lookup("microphone");
		        if (!microphone.startRecording()) {
		            JOptionPane.showMessageDialog(null,"Cannot start microphone.","Error",JOptionPane.ERROR_MESSAGE);
		            recognizer.deallocate();
		            System.exit(1);
		        }

		        // loop the recognition until the program exits.
		        while (true) {
		        	listening.setVisible(true);
		            Result result = recognizer.recognize();
		            
		        if (result != null) {
		                resultText = result.getBestFinalResultNoFiller();
		                userSpeechDisp.setText("You said: " + '\n' + resultText );
		                
		                
 //---------------------------------| Vocal speech instantiation |-------------------------------------//   
		                TxtToSpch v = new TxtToSpch("kevin16");
          
//---------------------------------| Basic Introduction Part |-------------------------------------//            
		//Greetings
		if((resultText.equalsIgnoreCase("hey computer")) ||
		  (resultText.equalsIgnoreCase("hi computer")))
		        {
		        try{
		        	
		        	String[] greet = {
							  //"Hello there!",
							 // "What can I help you with?",
							  "Yes, Badis? "
							  };
					String greet_ran = greet[ran.nextInt(greet.length)];
					/*slnDisp.setText("Yes, Badis? ");
					answerToStr ="Yes, Badis? ";*/
					//
					slnDisp.setText(greet_ran);
					answerToStr = slnDisp.getText();
					
					/*textToSpeech=("Say "+"\""+answerToStr+"\"");
					Runtime.getRuntime().exec(textToSpeech);*/
					v.say(answerToStr);
					TimeUnit.SECONDS.sleep(4);
					microphone.clear();					
		          } catch(Exception er) {} }
		                		 		                
		                
		// Salam
					if(resultText.equalsIgnoreCase("salam a lie com"))
				        {
				        try{
				        slnDisp.setText("wa alaykom salam") ;
				        answerToStr = slnDisp.getText();
				        /*textToSpeech=("Say "+"\""+answerToStr+"\"");
						Runtime.getRuntime().exec(textToSpeech);*/
						v.say(answerToStr);
						TimeUnit.SECONDS.sleep(4);
						microphone.clear();
			          } catch(Exception er) {} }
		
		
		// Name              
					if(resultText.equalsIgnoreCase("do you have a name"))
				        {
				        try{
				        slnDisp.setText("My name is ASUS!") ;
				        answerToStr = slnDisp.getText();
				        /*textToSpeech=("Say "+"\""+answerToStr+"\"");
						Runtime.getRuntime().exec(textToSpeech);*/
						v.say(answerToStr);
						TimeUnit.SECONDS.sleep(4);
						microphone.clear();
				          } catch(Exception er) {} }
	
		 
		
			
			//Being
			if(resultText.equalsIgnoreCase("how are you") ||
					   (resultText.equalsIgnoreCase("what's up"))) 
					{
					try{
					slnDisp.setText("I'm good, thanks for asking") ;
					answerToStr = slnDisp.getText();
					/*textToSpeech=("Say "+"\""+answerToStr+"\"");
					Runtime.getRuntime().exec(textToSpeech);*/
					v.say(answerToStr);
					TimeUnit.SECONDS.sleep(6);
					microphone.clear();   
					 } catch(Exception er) {} }
			
			//Age               
			if(resultText.equalsIgnoreCase("how old are you")||
			(resultText.equalsIgnoreCase("what is your age")))
			{
			try{
			slnDisp.setText("No idea... maybe one or two years old ") ;
			answerToStr = slnDisp.getText();
			/*textToSpeech=("Say "+"\""+answerToStr+"\"");
			Runtime.getRuntime().exec(textToSpeech);*/
			v.say(answerToStr);
			TimeUnit.SECONDS.sleep(8);
			microphone.clear();
			        			   
			    } catch(Exception er) {} }
			
//--------------------------------------| Humor Puns and Jokes |-------------------------------------//            			 
			//Coffee
			if(resultText.equalsIgnoreCase("make me a coffee"))
					                {
				try{
					slnDisp.setText("WHAT? MAKE IT YOURSELF!") ;
					answerToStr = slnDisp.getText();
					/*textToSpeech=("Say "+"\""+answerToStr+"\"");
					Runtime.getRuntime().exec(textToSpeech);*/
					v.say(answerToStr);
					TimeUnit.SECONDS.sleep(4);
					microphone.clear();
					
				    } catch(Exception er) {}}
			else if
			(resultText.equalsIgnoreCase("sue dough make me a coffee"))
            {
            	try{
            		userSpeechDisp.setText("You said: SUDO make me a coffee ");
            		slnDisp.setText("OK Master") ;
            		answerToStr = slnDisp.getText();
            		/*textToSpeech=("Say "+"\""+answerToStr+"\"");
					Runtime.getRuntime().exec(textToSpeech);*/
					v.say(answerToStr);
					TimeUnit.SECONDS.sleep(4);
					microphone.clear();
            	} catch(Exception er) {}}
			
			//angry
			

			//Jokes
			if(resultText.equalsIgnoreCase("how is the best in the world"))			  
			
            {
				
				try{
					String jokes = ("Of course is not you! :p");;
					//String jokes_ran = jokes[ran.nextInt(jokes.length)];
					//slnDisp.setFont(new Font("Cambria" , Font.BOLD, 17));
	            	slnDisp.setText(jokes) ;
	            	answerToStr = slnDisp.getText();
	            	/*textToSpeech=("Say "+"\""+answerToStr+"\"");
					Runtime.getRuntime().exec(textToSpeech);*/
					v.say(answerToStr);
					TimeUnit.SECONDS.sleep(4);
					microphone.clear();
					} catch(Exception er) {}}
			
			else if(resultText.equalsIgnoreCase("how is the best professor"))			  
				
            {
				
				try{
					String jokes = ("she is Miss Guerdelli Hajer");;
					//String jokes_ran = jokes[ran.nextInt(jokes.length)];
					//slnDisp.setFont(new Font("Cambria" , Font.BOLD, 17));
	            	slnDisp.setText(jokes) ;
	            	answerToStr = slnDisp.getText();
	            	/*textToSpeech=("Say "+"\""+answerToStr+"\"");
					Runtime.getRuntime().exec(textToSpeech);*/
					v.say(answerToStr);
					TimeUnit.SECONDS.sleep(4);
					microphone.clear();
					} catch(Exception er) {}}
			
			
			//Nice Once
			if(resultText.equalsIgnoreCase("nice one"))
	        {
	        try{
	        	String [] chs = {"Just saying\"NICE ONE\" in an accent wont make you British!!! :/ ,",
	        			 "Thanks,"};
	        	String chs_ran = chs[ran.nextInt(chs.length)];
	        slnDisp.setText(chs_ran) ;
	        answerToStr = slnDisp.getText();
	        /*textToSpeech=("Say "+"\""+answerToStr+"\"");
			Runtime.getRuntime().exec(textToSpeech);*/
			v.say(answerToStr);
			TimeUnit.SECONDS.sleep(4);
			microphone.clear();
	          } catch(Exception er) {} }

			
			
			
//---------------------------------| Time and System Apps |-------------------------------------//            
		 
			// Time 
		 if(resultText.equalsIgnoreCase("what time is it") || 
			resultText.equalsIgnoreCase("what is the current time")) 
			                {
		try{
			SimpleDateFormat time = new SimpleDateFormat("hh:mm a");
			String formattedDate = time.format(new Date()).toString();
			slnDisp.setText("It is " + formattedDate ) ;
			answerToStr = slnDisp.getText();
			/*textToSpeech=("Say "+"\""+answerToStr+"\"");
			Runtime.getRuntime().exec(textToSpeech);*/
			v.say(answerToStr);
			TimeUnit.SECONDS.sleep(4);
			microphone.clear();
		    } catch(Exception er) {}
		        			}
		 //System Preferences
		 if(resultText.equalsIgnoreCase("open system preferences"))
			{
				try{
					slnDisp.setText("Opening System Preferences" ) ;
					pro =new ProcessBuilder("/usr/bin/open", "-a", "System Preferences").start();;
					answerToStr = slnDisp.getText();
					/*textToSpeech=("Say "+"\""+answerToStr+"\"");
					Runtime.getRuntime().exec(textToSpeech);*/
					v.say(answerToStr);
					TimeUnit.SECONDS.sleep(4);
					microphone.clear();
				} catch(Exception er) {}
			}   
		
		 if (resultText.equalsIgnoreCase("close system preferences"))
		    {
		        try{
		        slnDisp.setText("Closing System Preferences" ) ;
		        pro =new ProcessBuilder("/usr/bin/killall", " -kill-a", "System Preferences").start();
		        answerToStr = slnDisp.getText();
		        /*textToSpeech=("Say "+"\""+answerToStr+"\"");
				Runtime.getRuntime().exec(textToSpeech);*/
				v.say(answerToStr);
				TimeUnit.SECONDS.sleep(4);
				microphone.clear();
		        }catch(Exception ae){}
		    }
		 
		 
		 //Dictionary
		 if(resultText.equalsIgnoreCase("open dictionary"))
			{
				try{
					
					slnDisp.setText("Opening Dictionary" ) ;
					pro= Runtime.getRuntime().exec("/usr/bin/open -a Dictionary.app");;
					answerToStr = slnDisp.getText();
					textToSpeech=("Say "+"\""+answerToStr+"\"");
					Runtime.getRuntime().exec(textToSpeech);
					TimeUnit.SECONDS.sleep(4);
					microphone.clear();
				} catch(Exception er) {}
			}   
		
		 if (resultText.equalsIgnoreCase("close dictionary"))
		    {
		        try{	
		        slnDisp.setText("Closing Dictionary" ) ;
		        pro=Runtime.getRuntime().exec("/usr/bin/killall -kill Dictionary");
		        answerToStr = slnDisp.getText();
				textToSpeech=("Say "+"\""+answerToStr+"\"");
				Runtime.getRuntime().exec(textToSpeech);
				TimeUnit.SECONDS.sleep(4);
				microphone.clear();
		        }catch(Exception ae){}
		    }
		 
		 
		 //Calculator
		 if(resultText.equalsIgnoreCase("open calculator"))
			{
				try{
					slnDisp.setText("Opening Calculator" ) ;
					pro= Runtime.getRuntime().exec("cmd /c start  calc");;
					answerToStr = slnDisp.getText();
					/*textToSpeech=("Say "+"\""+answerToStr+"\"");
					Runtime.getRuntime().exec(textToSpeech);*/
					v.say(answerToStr);
					
					TimeUnit.SECONDS.sleep(4);	
					microphone.clear();
				} catch(Exception er) {}
			}   
		
		 else if (resultText.equalsIgnoreCase("close calculator"))
		    {
		        try{
		        slnDisp.setText("Closing Calculator" ) ;
		        pro=Runtime.getRuntime().exec("cmd /c start taskkill /im calc.exe/f");
		        answerToStr = slnDisp.getText();
			/*	textToSpeech=("Say "+"\""+answerToStr+"\"");
				Runtime.getRuntime().exec(textToSpeech);*/
		       
				TimeUnit.SECONDS.sleep(4);
				microphone.clear();
		        }catch(Exception ae){}
		    }
		   
			 
		                
		// Terminal
				 if(resultText.equalsIgnoreCase("open terminal"))
					{
					 	try{
							pro=Runtime.getRuntime().exec("cmd /c start   cmd");
							slnDisp.setText("Opening Terminal" ) ;
							answerToStr = slnDisp.getText();
							textToSpeech=("Say "+"\""+answerToStr+"\"");
							Runtime.getRuntime().exec(textToSpeech);
							TimeUnit.SECONDS.sleep(4);
							microphone.clear();
					 	} catch(Exception er) {}
					}              
		
				 else if (resultText.equalsIgnoreCase("close terminal"))
				    {
				        try{
				        slnDisp.setText("Closing Terminal" ) ;
				        pro=Runtime.getRuntime().exec("cmd /c start taskkill cmd.exe/f");
				        answerToStr = slnDisp.getText();
						textToSpeech=("Say "+"\""+answerToStr+"\"");
						Runtime.getRuntime().exec(textToSpeech);
						TimeUnit.SECONDS.sleep(4);
						microphone.clear();
				        }catch(Exception ae){}
				    }
				 
				 //Camera
				
				
//---------------------------------| Websites and Apps |-------------------------------------//            
		                   					
				 // Ecommerce
				   if(resultText.equalsIgnoreCase("open my shop")) 
			        {
					   try{
						   userSpeechDisp.setText("You said: \n open My Shop");
						   slnDisp.setText("Opening: localhost/ My Shop ") ;
						   String url_open ="http://localhost/ecom2/";
						   java.awt.Desktop.getDesktop().browse(java.net.URI.create(url_open));
						   answerToStr = slnDisp.getText();
							textToSpeech=("Say "+"\""+answerToStr+"\"");
							Runtime.getRuntime().exec(textToSpeech);
							TimeUnit.SECONDS.sleep(4);
							microphone.clear();
					   } catch(Exception er) {}
			        }
				   
				 // Google
	   if(resultText.equalsIgnoreCase("open goo gall")) 
        {
		   try{
			   userSpeechDisp.setText("You said: \n open Google");
			   slnDisp.setText("Opening: www.google.com ") ;
			   String url_open ="http://www.google.com";
			   java.awt.Desktop.getDesktop().browse(java.net.URI.create(url_open));
			   answerToStr = slnDisp.getText();
				textToSpeech=("Say "+"\""+answerToStr+"\"");
				Runtime.getRuntime().exec(textToSpeech);
				TimeUnit.SECONDS.sleep(4);
				microphone.clear();
		   } catch(Exception er) {}
        }
	   
	   //YouTube
	   if(resultText.equalsIgnoreCase("open you tube")) 
       {
		   try{
			   slnDisp.setText("Opening: www.youtube.com ") ;
			   String url_open ="http://www.youtube.com";
			   java.awt.Desktop.getDesktop().browse(java.net.URI.create(url_open));
			   answerToStr = slnDisp.getText();
				textToSpeech=("Say "+"\""+answerToStr+"\"");
				Runtime.getRuntime().exec(textToSpeech);
				TimeUnit.SECONDS.sleep(4);
				microphone.clear();
		   } catch(Exception er) {}
       }
	     
	   //Facebook
	   if(resultText.equalsIgnoreCase("open face book")) 
       {
		   try{
			   slnDisp.setText("Opening: www.facebook.com ") ;
			   String url_open ="http://www.facebook.com";
			   java.awt.Desktop.getDesktop().browse(java.net.URI.create(url_open));
			   answerToStr = slnDisp.getText();
				textToSpeech=("Say "+"\""+answerToStr+"\"");
				Runtime.getRuntime().exec(textToSpeech);
				TimeUnit.SECONDS.sleep(4);
				microphone.clear();
		   } catch(Exception er) {}
       }
	 
	   //Facebook
	   if(resultText.equalsIgnoreCase("open tweet err")) 
       {
		   try{
			   slnDisp.setText("Opening: www.twitter.com ") ;
			   String url_open ="http://www.twitter.com";
			   java.awt.Desktop.getDesktop().browse(java.net.URI.create(url_open));
			   answerToStr = slnDisp.getText();
				textToSpeech=("Say "+"\""+answerToStr+"\"");
				Runtime.getRuntime().exec(textToSpeech);
				TimeUnit.SECONDS.sleep(4);
				microphone.clear();
		   } catch(Exception er) {}
       }
         // iTunes
		
		 
		
		 //Google Chrome
		 if(resultText.equalsIgnoreCase("open chrome") ||
		(resultText.equalsIgnoreCase("open browser")))
			{
				try{
					slnDisp.setText("Opening chrome" ) ;
					 pro=Runtime.getRuntime().exec("cmd /c start chrome");
					answerToStr = slnDisp.getText();
					/*textToSpeech=("Say "+"\""+answerToStr+"\"");
					Runtime.getRuntime().exec(textToSpeech);*/
					TimeUnit.SECONDS.sleep(4);	
					microphone.clear();
				} catch(Exception er) {}
			}    
		 else if (resultText.equalsIgnoreCase("close chrome") ||
			(resultText.equalsIgnoreCase("close browser")))
		    {
		        try{	
		        slnDisp.setText("Closing Chrome") ;
		        pro=Runtime.getRuntime().exec("cmd /c start taskkill /im chrome.exe /f");
		        pro =new ProcessBuilder("/usr/bin/killall", " -kill-a", "Google Chrome").start();
		        answerToStr = slnDisp.getText();
				textToSpeech=("Say "+"\""+answerToStr+"\"");
				Runtime.getRuntime().exec(textToSpeech);
				TimeUnit.SECONDS.sleep(4);
				microphone.clear();
		        }catch(Exception ae){}
		    }
		 
		 //VLC
		 if(resultText.equalsIgnoreCase("open v l c"))
			{
				try{
					slnDisp.setText("Opening VLC Media Player" ) ;
					pro= Runtime.getRuntime().exec("/usr/bin/open -a VLC.app");;
					answerToStr = slnDisp.getText();
					textToSpeech=("Say "+"\""+answerToStr+"\"");
					Runtime.getRuntime().exec(textToSpeech);
					TimeUnit.SECONDS.sleep(4);
					microphone.clear();
				} catch(Exception er) {}
			}   
		
		 else if (resultText.equalsIgnoreCase("close v l c"))
		    {
		        try{
		        slnDisp.setText("Closing VLC Media Player" ) ;
		        pro=Runtime.getRuntime().exec("/usr/bin/killall -kill VLC");
		        answerToStr = slnDisp.getText();
				textToSpeech=("Say "+"\""+answerToStr+"\"");
				Runtime.getRuntime().exec(textToSpeech);
				TimeUnit.SECONDS.sleep(4);
				microphone.clear();
		        }catch(Exception ae){}
		    }
		 
		 
		 //Photoshop
		 if (resultText.equalsIgnoreCase("open photo shop"))
		    {
		        try{
		        slnDisp.setText("Opening Adobe Photoshop");
		        pro =new ProcessBuilder("/usr/bin/open", "-a", "Adobe Photoshop CS6").start();
		        answerToStr = slnDisp.getText();
				textToSpeech=("Say "+"\""+answerToStr+"\"");
				Runtime.getRuntime().exec(textToSpeech);
				TimeUnit.SECONDS.sleep(4);
				microphone.clear();
		        }catch(Exception ae){}
		    }
		 else if (resultText.equalsIgnoreCase("close photo shop"))
				    {
				        try{
				        slnDisp.setText("Closing Adobe Photoshop") ;
				        pro =new ProcessBuilder("/usr/bin/killall", " -kill-a", "Adobe Photoshop CS6").start();
				        answerToStr = slnDisp.getText();
						textToSpeech=("Say "+"\""+answerToStr+"\"");
						Runtime.getRuntime().exec(textToSpeech);
						TimeUnit.SECONDS.sleep(4);
						microphone.clear();
				        }catch(Exception ae){}}
		  		if (resultText.equalsIgnoreCase("stop recognition") ||(resultText.equalsIgnoreCase("good bye computer")))
		    {
		        try{
		        slnDisp.setText("Good Bye! See you again") ;
		        answerToStr = slnDisp.getText();
				textToSpeech=("Say "+"\""+answerToStr+"\"");
				Runtime.getRuntime().exec(textToSpeech);
				TimeUnit.SECONDS.sleep(4);
				microphone.clear();
		        System.exit(0);
		        }catch(Exception ae){}}
		            } else {
		            	slnDisp.setText("I can't hear what you said.\n");
		        
		            }
		        }
		    			}

		}
			
