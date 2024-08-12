import java.awt.*; 
import java.net.URL; 
import java.util.function.Function;
import javax.swing.*;
import java.awt.geom.Line2D;
import javax.swing.JOptionPane;
public class Proiect extends Frame{ 
Toolkit tool; 
int ww, hh; 
public Image backg, info; 
public SimPanel simPanel; 
public InfoPanel infoPanel; 
TextField tf; 
Button start; 
Font f = new Font("TimesRoman", 1, 14); 

public static String ecuatie;

public static void main (String args[]){new Proiect();} 
public Proiect(){ 
tool=getToolkit();
Dimension res=tool.getScreenSize(); 
ww = res.width; 
hh = res.height; 
setResizable(false); 
setTitle("Proiectul 1"); 
setIconImage(tool.getImage(GetResources("images/ico.gif"))); 
setBackground(new Color(67, 134, 187)); 
setLayout(null); 
loadImage(); 
tf = new TextField("2^x");
tf.setForeground(Color.blue);
tf.setFont(f); 
tf.requestFocus(); 
add(tf); 
tf.setBounds(275, 55, 500, 20); 
start = new Button("Deseneaza"); 
add(start); 
start.setBounds(800, 55, 80, 20); 

ecuatie=tf.getText();

//**** Cadrul unde desenez functia**********
simPanel = new SimPanel(this); 
add(simPanel); 
simPanel.setBounds(400, 100, 600, 600); 
//***************************************

/*
infoPanel = new InfoPanel(this); 
add(infoPanel); 
infoPanel.setBounds(25, 625, 600, 115); 
*/

resize(ww, hh); 
move(0,0); 
setVisible(true);}
public java.net.URL GetResources(String s) {return this.getClass().getResource(s);} 
public void loadImage(){ 
try { 
MediaTracker mediatracker = new MediaTracker(this); 
backg = tool.getImage(GetResources("images/backg.jpg")) ; 
mediatracker.addImage(backg, 0); 
//info = tool.getImage(GetResources("images/buffon.gif")); 
//mediatracker.addImage(info, 0); 
mediatracker.waitForAll(); 
} 
catch(Throwable throwable){}
} 
public void paint(Graphics g){ 
for(int i = 0; i <= (int)(ww/200); i++) 
for(int j = 0; j <= (int)(hh/200); j++) 
g.drawImage(backg, i*200, j*200, this); 
g.setColor(Color.white); 
g.setFont(f); 
g.drawString("Functia : ", 75, 70); 
//g.drawImage(info, 655, 135, this);


}

public boolean bpress=false;
public boolean handleEvent(Event e){ 
if(e.id==Event.WINDOW_DESTROY){System.exit(0);}
switch(e.id) { 
default: break; 
case 1001: 
if(e.target == start) { 
tf.requestFocus(); 
//simPanel.calculeaza(tf.getText());
simPanel.bpress=true;
ecuatie=tf.getText();
simPanel.repaint();
} 
break;
}
return super.handleEvent(e);}
} 

//**********************************

class InfoPanel extends Panels { 
public int nraruncari; 
public int nrintersectii; 
public double PI; 
Font f; 
Color col1 = Color.white; 
Color col2 = Color.yellow; 
public InfoPanel(Proiect buffon) { 
super(buffon.backg); 
this.f = buffon.f;
}
public void paint(Graphics g) { 
super.paint(g); 
g.setFont(f); 
int w = 50; 
g.setColor(col1); 
g.drawString("NUMARL DE ARUNCARI :", w, 25); 
g.drawString("" + nraruncari, w + 300, 25); 
g.drawString("NUMARL DE CAZURI FAVORABILE :", w, 50); 
g.drawString("" + nrintersectii, w + 300, 50); 
g.drawString("ESTIMAREA LUI PI :", w, 75); 
g.drawString("" + PI, w + 300, 75); 
g.drawString("NUMARUL PI :", w, 100); 
g.setColor(col2); 
g.drawString("3.141592653589793238462643383", w + 300, 100); 
}

public void setInfo(int nraruncari, int nrintersectii, double PI){ 
this.nraruncari = nraruncari; 
this.nrintersectii = nrintersectii; 
this.PI = PI; 
repaint(); 
} 
} 


//*******************************************// 


class SimPanel extends Panels { 
Proiect buffon; 
Color col1= Color.yellow; 
Point starts[]; 
Point ends[]; 
int nrAruncari; 
int nrCazuriFavorabile; 
double pi; 

public double X,a=5.0,b=5.0,m,M;
public double N=300;
double raport;


public SimPanel(Proiect buffon) { 
super(buffon.backg); 
this.buffon = buffon; 
}





//bun

public int da_pozitie(String expresie,int codCaracterCautat){//da ultima aparitie pentru caracterul ce are codul codCaracterCautat (sare peste paranteze)
			int temp=0;
			for(int i=expresie.length()-1;i>=0;i--){
				if((temp==0)&&(expresie.charAt(i)==codCaracterCautat))return i;
				if(expresie.charAt(i)==')')temp++;
				if(expresie.charAt(i)=='(')temp--;
			}
			return -1;
}


//*****Aici facem magia****************************** 
/*Point daCoord(double x,double y){
			int x0=(int)((x-a)*raport+50+(400-(b-a)*raport)/2);
			int y0=(int)((M-y)*raport+50+(400-(M-m)*raport)/2);
			return new Point(x0,y0);	
		}*/
	
		public void calculeaza1(String expresie){
			//return 1;
			//repaint();
		}

public double calculeaza(String expresie){
	
	
			if(expresie.trim().length()==0)return 0;
			int pozitie;
			pozitie=da_pozitie(expresie,'+');
			if(pozitie>=0)return calculeaza(expresie.substring(0,pozitie))+calculeaza(expresie.substring(pozitie+1));
			pozitie=da_pozitie(expresie,'-');
			if(pozitie>=0)return calculeaza(expresie.substring(0,pozitie))-calculeaza(expresie.substring(pozitie+1));
			
			pozitie=da_pozitie(expresie,'^');
			if(pozitie>=0)return Math.pow(calculeaza(expresie.substring(0,pozitie)),calculeaza(expresie.substring(pozitie+1)));
			
			pozitie=da_pozitie(expresie,'*');
			if(pozitie>=0)return calculeaza(expresie.substring(0,pozitie))*calculeaza(expresie.substring(pozitie+1));
			pozitie=da_pozitie(expresie,'/');
			if(pozitie>=0){
				double val1=calculeaza(expresie.substring(0,pozitie));
				double val2=calculeaza(expresie.substring(pozitie+1));
				if(val2!=0)return val1/val2;
				return Double.MAX_VALUE;
			}
			
			if(expresie.startsWith("sqrt(")&&expresie.endsWith(")"))return Math.sqrt(calculeaza(expresie.substring(5,expresie.length()-1)));
			if(expresie.startsWith("abs(")&&expresie.endsWith(")"))return Math.abs(calculeaza(expresie.substring(4,expresie.length()-1)));
			if(expresie.startsWith("exp(")&&expresie.endsWith(")"))return Math.exp(calculeaza(expresie.substring(4,expresie.length()-1)));
			if(expresie.startsWith("log(")&&expresie.endsWith(")"))return Math.log10(calculeaza(expresie.substring(3,expresie.length()-1)));
                        if(expresie.startsWith("sin(")&&expresie.endsWith(")"))return Math.sin(calculeaza(expresie.substring(4,expresie.length()-1)));	
			if(expresie.startsWith("cos(")&&expresie.endsWith(")"))return Math.cos(calculeaza(expresie.substring(4,expresie.length()-1)));
			if(expresie.startsWith("acos(")&&expresie.endsWith(")"))return Math.acos(calculeaza(expresie.substring(5,expresie.length()-1)));
			if(expresie.startsWith("asin(")&&expresie.endsWith(")"))return Math.asin(calculeaza(expresie.substring(5,expresie.length()-1)));
			if(expresie.startsWith("tg(")&&expresie.endsWith(")"))return Math.tan(calculeaza(expresie.substring(3,expresie.length()-1)));
			if(expresie.startsWith("ln(")&&expresie.endsWith(")"))return Math.log(calculeaza(expresie.substring(3,expresie.length()-1)))/Math.log(Math.E);
			if(expresie.startsWith("exp(")&&expresie.endsWith(")"))return Math.exp(calculeaza(expresie.substring(4,expresie.length()-1)));
			if(expresie.startsWith("(")&&expresie.endsWith(")"))return calculeaza(expresie.substring(1,expresie.length()-1));
			if(expresie.startsWith("x")&&expresie.endsWith("x"))return X;
			if(expresie.startsWith("e")&&expresie.endsWith("e"))return Math.E;
			
			try{
				return Double.valueOf(expresie);
				
				
			}catch(Exception e){
					System.out.println("Expresia data nu este corecta!");
					//JOptionPane.showMessageDialog(null, "Expresia data nu este corecta!");
			}
			//System.out.println("aici");
			return 1;
			
		}
		
		
		
//******************************************************
public boolean bpress=false;


public double f(double x){
	X=x;
	
	return calculeaza(Proiect.ecuatie);
}
public void deseneaza(Graphics g){
	
	g.setColor(new Color(255,0,0)); 
	Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
for(double x=-299;x<=N;x++){
	
//g.drawLine(x,300+(int)(100*f(x)),x-1,300+(int)(100*f(x-1)));
//Shape l=new Line2D.Double(a+i*(b-a)/N,(f(a+i*(b-a)/N)),a+i*(b-a)/N-1,(f(a+i*(b-a)/N-1)));
Shape l=new Line2D.Double(x+300,300-(f(x)),x-1+300,300-(f(x-1)));
g2.draw(l);
}

}


public void paint(Graphics g) { 
super.paint(g); 
g.setColor(new Color(0,0,0)); 

if(bpress==false){
//*****desenez axele*********************
g.drawLine(0, 300, 600, 300); //axa OX
g.drawLine(300, 0, 300, 600);;//axa OY
}
//***************************************

if(bpress==true){

g.drawLine(0, 300, 600, 300); //axa OX
g.drawLine(300, 0, 300, 600);;//axa OY
deseneaza(g);
}



buffon.infoPanel.setInfo(nrAruncari, nrCazuriFavorabile, pi);
}}



class Panels extends Panel{
public Image im, im1; 
public Panels(Image im){this.im = im;} 
public void update(Graphics g) {paint(g);}
public void paint(Graphics g){
super.paint(g);
Dimension dimension = size(); 
im1= createImage(dimension.width, dimension.height); 
pan(im1.getGraphics()); 
g.drawImage(im1, 0, 0, this); 
} 
public void pan(Graphics g) { 
Dimension dimension = size(); 
int w = dimension.width; 
int h = dimension.height; 
Color color = getBackground();
g.setColor(color); 
g.fillRect(0, 0, w, h); 

for(int k = 0; k < w; k += im.getWidth(this)) 
	for(int l= 0; l< h; l+= im.getHeight(this)) 
		g.drawImage(im, k, l, this); 
g.setColor(color.brighter()); 
g.drawRect(1, 1, w - 2, h - 2); 
g.setColor(color.darker()); 
g.drawRect(0, 0, w - 2, h - 2); 
}
}



