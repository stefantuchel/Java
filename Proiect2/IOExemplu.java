
import java.awt.*;
import java.net.*;
import java.io.*;

class IOExemplu extends Frame {     
    String path, dir;
    URL simFile;            
    String[] data;
	
public static void main (String args[]){new IOExemplu();}


IOExemplu(){
    Dimension res=getToolkit().getScreenSize();
    setBackground(new Color(38, 104, 165));
    setForeground(new Color(255,255,0));    
    setResizable(false);       
    adaugaMenuBar(); 
    setTitle("Exemplu");                  
    resize(400,400);
    move((int)((res.width-400)/2),(int)((res.height-400)/2));
    show();     	
}


void adaugaMenuBar(){
	MenuBar men=new MenuBar();
	Menu f= new Menu("File");
	f.add("Open");
	f.add("-");
	f.add("Exit");
	men.add(f);
	setMenuBar(men);	
}


public boolean handleEvent(Event e){
		if(e.id==Event.WINDOW_DESTROY){
		  System.exit(0);
		  return true;
		  }else if(e.id==Event.ACTION_EVENT && e.target instanceof MenuItem){
		     if("Exit".equals(e.arg)){
			   System.exit(0);
			   return true;
	} else if("Open".equals(e.arg)){
		loadFile();
		return true;
	}else return true;
}else return false;
}

	

void loadFile(){
	try{
		FileDialog fd=new FileDialog(this,"Open *.sim File",0);
		if(dir!=null) fd.setDirectory(dir);
		//fd.setFile("*.sim");
		fd.setVisible(true);
		if(fd.getFile()!=null){
			dir=fd.getDirectory();
			String fisier=fd.getFile();
			path=dir+fisier;
			try{simFile=new URL("file:/"+path);}
			catch(MalformedURLException e){}
			setTitle("File Loaded. Simulating...");
			readDataInput(simFile);
			writeDataOutput();
			openDataOutput();
			setTitle("Finished");
		}
	}
	catch(Exception e){}
}

/*
void LoadStream(URL classFile){	
  try{
		FileDialog fd=new FileDialog(this,"Open *.sim File",0);
		if(dir!=null) fd.setDirectory(dir);
		fd.setFile("*.sim");
		fd.setVisible(true);
		if(fd.getFile()!=null){
			dir=fd.getDirectory();
			String fisier=fd.getFile();
			path=dir+fisier;
			try{simFile=new URL("file:/"+path);}
			catch(MalformedURLException e){}
			setTitle("File Loaded. Simulating...");
			readDataInput(simFile);
			writeDataOutput();
			openDataOutput();
			setTitle("Finished");
		}
	}
	catch(Exception e){}
}   
*/
FileWriter fw;
BufferedWriter bw;  
void writeFile(File f) throws Exception {
    if (path!="") {
        if(f.exists()) f.delete();
        FileWriter fw = new FileWriter(f);
        BufferedWriter bw = new BufferedWriter(fw);
        for (int i = 0; i < data.length; i++) bw.write(modifica(data[i]) + "\n");
        bw.flush();
        bw.close();
    }
}

String modifica(String s){return "zzz" + s;}


	
void readDataInput(URL simF){		
		String line = "";
    		try {
				InputStream is = simF.openStream();
        		DataInputStream dis = new DataInputStream(new BufferedInputStream(is));
        		try {	
            			while ((line = dis.readLine()) != null) 
						{
            				
            				if(line.startsWith("SIM_CONDITIONS"))
							{
            					line = line.substring(line.indexOf('=')+1);
								
	        					try{q1 = Integer.parseInt(line.substring(0,line.indexOf(' ',0)));}
	        					catch(NumberFormatException e){}
								
	        					line = line.substring(line.indexOf('=')+1);
								
	        					try{q2 = Integer.parseInt(line.substring(0,line.indexOf(' ',0)));}
	        					catch(NumberFormatException e){}   
								
								line = line.substring(line.indexOf('=')+1);
								
	        					try{q3 = Integer.parseInt(line.substring(0,line.indexOf(' ',0)));}
	        					catch(NumberFormatException e){} 
								
								line = line.substring(line.indexOf('=')+1);
								
	        					try{k = Integer.parseInt(line.substring(0,line.indexOf(' ',0)));}
	        					catch(NumberFormatException e){} 
								
								line = line.substring(line.indexOf('=')+1);
								
	        					try{r = Integer.parseInt(line.substring(0,line.indexOf(' ',0)));}
	        					catch(NumberFormatException e){} 
								
	        					continue;
							
            				}
							
							if(line.startsWith("processes_count"))
							{
            					line = line.substring(line.indexOf('=')+1);
								
	        					try{processes_count = Integer.parseInt(line.substring(0,line.indexOf(' ',0)));}
	        					catch(NumberFormatException e){}
								procese=new Proces[processes_count];
								line=dis.readLine();
								line=dis.readLine();
								for (int m=0; m<processes_count; m++)
								{
									if(line.startsWith("Process #"+(m+1)))
									{
									//nume_proc
										line=dis.readLine();
										name = line.substring(line.indexOf('=')+1);
									//timp_S_PROCES
										line=dis.readLine();
										line=line.substring(line.indexOf('=')+1);
										try{start = Integer.parseInt(line.substring(0,line.indexOf(' ',0)));}
										catch(NumberFormatException e){}
									//CATE FAZE ARE processul
										line=dis.readLine();
										line=line.substring(line.indexOf('=')+1);
										try{nr_faze = Integer.parseInt(line.substring(0,line.indexOf(' ',0)));}
										catch(NumberFormatException e){}
									//fazele
										faze=new Phase[nr_faze];
										for (int i=0; i<nr_faze; i++)
										{
											line=dis.readLine();
											line = line.substring(line.indexOf('=')+1);
										
											try{cpu = Integer.parseInt(line.substring(0,line.indexOf(' ',0)));}
											catch(NumberFormatException e){}
											
											line = line.substring(line.indexOf('=')+1);
											
											try{io = Integer.parseInt(line.substring(0,line.indexOf(' ',0)));}
											catch(NumberFormatException e){}   
											
											line = line.substring(line.indexOf('=')+1);
											
											try{repeat = Integer.parseInt(line.substring(0,line.indexOf(' ',0)));}
											catch(NumberFormatException e){} 
											faze[i]=new Phase(cpu,io,repeat);
										}
									procese[m]=new Proces(name,(char)(65+m),start,nr_faze,faze);
									line=dis.readLine();
									line=dis.readLine();
									}								
								}
								continue;
							}
            			}
				
        		}
        		catch (EOFException e) {}	
        		is.close();	
    		} 
    		catch (IOException e) {}
} 
void writeDataOutput(){
	
		try{
			fw = new FileWriter(new File("output.htm"));
			bw = new BufferedWriter(fw);   
			bw.write("<html><head><title>Simulator</title></head><body BACKGROUND=\"bkg.jpg\" BGPROPERTIES=\"fixed\"><center>");
			bw.write("<a name='top'></a>");
			bw.write("<h1>THREADS SIMULATION</h1><br>");
			
			
			bw.write("<table width=50%><tr><td><a href='#sid'>Simulation input Data</a></td><td></td></tr><tr><td><a href='#pd'>Processes Data</a></td><td><a href='#p1'>Proces #1</a><br><a href='#p2'>Proces #2</a><br><a href='#p3'>Proces #3</a><br><a href='#p4'>Proces #4</a><br><a href='#p5'>Proces #5</a><br></td></tr><tr><td><a href='#sod'>Simulation Output Data</a><td/><td>&nbsp;<td/></tr></table>");
			bw.write("<br><br><br><br>");
			
			
			bw.write("<a name='sid'></a>");
			bw.write("<h3>SIMULATION INPUT DATA</h3>");
			bw.write("<table width=90% border='2' ><tr align='center'><td>MAX PRIORITY</td><td>NORMAL PRIORITY</td><td>MIN PRIORITY</td><td>PENALTY LIMIT</td><td>AWARD LIMIT</td></tr><tr align='center'><td>q1 = "+q1+"</td><td>q2 = "+q2+"</td><td>q3 = "+q3+"</td><td>k = "+k+"</td><td>r = "+r+"</td></tr></table>");
			bw.write("<div align='left'><a href='#top'>top</a>");
			bw.write("<br><br><br><br>");
		
			bw.write("<div align='left'><a name='pd'></a>");
			bw.write("<h3>PROCESSES DATA</h3>");
			bw.write("<div align='left'>Processes_Count = "+processes_count+"</div><br><br><br>");
			for (int i=0; i<processes_count;i++)
			{
				bw.write("<h3>PROCESS #"+(i+1)+"</h3>");
				bw.write("<table width=100% border='2' >");
				bw.write("<tr align='center'>");
				bw.write("<th width=20%>NAME</th>");
				bw.write("<th width=20%>ALIAS</th>");
				bw.write("<th width=20%>START TIME</th>");
				bw.write("<th width=20%>PHASES COUNT</th>");
				bw.write("</tr> ");
				
				bw.write("<tr align='center'>");
				bw.write("<td>"+procese[i].NAME+"</td>");
				bw.write("<td>"+procese[i].ALIAS+"</td>");
				bw.write("<td>"+procese[i].TIME_START+"</td>");
				bw.write("<td>"+procese[i].PHASES_COUNT+"</td>");
				bw.write("</tr> ");
				
				bw.write("<tr align='center'>");
				bw.write("<th width=20%>PHASE COUNT</th>");
				bw.write("<th width=20%>CPU TIMES COUNT</th>");
				bw.write("<th width=20%>I/O TIMES COUNT</th>");
				bw.write("<th width=20%>REPEAT COUNT</th>");
				bw.write("</tr> ");
				//fazele
				for (int j=0; j<procese[i].PHASES_COUNT;j++)
				{
					bw.write("<tr align='center'>");
					bw.write("<td>"+(j+1)+"</td>");
					bw.write("<td>"+procese[i].phases[j].CPU_TIMES_COUNT+"</td>");
					bw.write("<td>"+procese[i].phases[j].IO_TIMES_COUNT+"</td>");
					bw.write("<td>"+procese[i].phases[j].REPEAT_COUNT+"</td>");
					bw.write("</tr>");
				}
				bw.write("</table>");
				bw.write("<br><br>");
				bw.write("<div align='left'><a href='#top'>top</a>");
			}
		
				bw.write("<a name='sod'></a>");
				bw.write("<h3>SIMULATION OUTPUT DATA</h3><br>");
				bw.write("<table width=100% border='2' >");
				bw.write("<tr align='center'>");
				bw.write("<th width=5%>TIME</th>");
				bw.write("<th width=5%>CPU</th>");
				bw.write("<th width=5%>I/O</th>");
				bw.write("<th width=10%>Q1 QUEUE</th>");
				bw.write("<th width=10%>Q2 QUEUE</th>");
				bw.write("<th width=10%>Q3 QUEUE</th>");
				bw.write("<th width=10%>I/O QUEUE</th>");
				bw.write("</tr> ");
				proiect();
				bw.write("</table>");
			bw.write("</body></html>");
			bw.flush();
			bw.close();	        	
        	}
		catch(IOException e){}
	}
	int q1, q2, q3, k, r,processes_count,start,nr_faze,cpu,io,repeat;
	String name;
	Phase faze[];
	Proces procese[];
	static long time=0;
	String Q1="", Q2="", Q3="", QIO="";
	boolean finished=false;
	Proces proces_curent=null,proces_IO=null;
	char alias='-', alias2='-';

void proiect()throws IOException
	{
		int max=0,count=0,count2=0,i,countq2=0,countq3=0;
		String Qadaug="",Qadaug2="",Qadaug3="";
		boolean set=false;
		for (i=0;i<processes_count;i++)
			if(procese[i].TIME_START>max)
				max=procese[i].TIME_START;
		
		for (i=0;i<processes_count;i++)
			if(procese[i].TIME_START==0)
				Q1+=procese[i].ALIAS;
				
				
		
		while (!finished)
		{
			if (time<=max&&time>0)
				for (i=0;i<processes_count;i++)
					if(procese[i].TIME_START==time)
						Qadaug+=procese[i].ALIAS;
			
			if (Q1.compareTo("")!=0||count!=0)
			{
			if (countq2<q2&&countq2!=0) {Q2=alias+Q2; countq2=0;}
			if (countq3<q3&&countq3!=0) {Q3=alias+Q3; countq3=0;}
			
				if (count==0)
				{
					alias=Q1.charAt(0);
					proces_curent=daProces(alias);
					Q1=Q1.substring(1);
				}			
					if (proces_curent.PHASES_COUNT!=proces_curent.faza_curenta)
					{
						proces_curent.phases[proces_curent.faza_curenta].CPU_TIMES_COUNT--;	
					}
					else
					{
						scrie_rand(time,alias,alias2,Q1,Q2,Q3,QIO);
						time++;
						count=0;
						proces_curent.finished=true;
						bw.write("<tr><td colspan=7 bgcolor='#ff0000' align='center'>Process "+alias+" is finished.</td></tr>");
						finished=test();
						continue;
					}
				
				scrie_rand(time,alias,alias2,Q1,Q2,Q3,QIO);
				
				if (proces_IO!=null)
				{
					if (count2<proces_IO.phases[proces_IO.faza_curenta].IO_TIMES_COUNT)
						count2++;
					if (proces_IO.phases[proces_IO.faza_curenta].IO_TIMES_COUNT==count2)
					{
					
				
						switch (proces_IO.from)
							{
								case 1:	Qadaug+=proces_IO.ALIAS; break;
								case 2: Q2+=proces_IO.ALIAS; break;
								case 3: Q3+=proces_IO.ALIAS; break;
							}

						if (proces_IO.phases[proces_IO.faza_curenta].REPEAT_COUNT==0)
							{
								proces_IO.faza_curenta++;
								bw.write("<tr><td colspan=7 bgcolor='#ffff00' align='center'>Phase #"+proces_IO.faza_curenta+" of the Process #"+((int)(alias2)-64)+" is finished</td></tr>");
							}
							
						count2=0;
						proces_IO=null;
						alias2='-';
						
						if (QIO.compareTo("")!=0)
						{
							alias2=QIO.charAt(0);
							QIO=QIO.substring(1);
							proces_IO=daProces(alias2);
						
							
							set=true;
						}
					}
				}
						
					if (proces_curent.phases[proces_curent.faza_curenta].CPU_TIMES_COUNT==0)
					{
						proces_curent.phases[proces_curent.faza_curenta].REPEAT_COUNT--;
						proces_curent.phases[proces_curent.faza_curenta].CPU_TIMES_COUNT=proces_curent.phases[proces_curent.faza_curenta].cpu_bak;
						proces_curent.award++;
						
						QIO+=alias;
						daProces(alias).from=1;
							
						if (!set)
						{
							if (count2==0)
							{
								alias2=QIO.charAt(0);
								QIO=QIO.substring(1);
								proces_IO=daProces(alias2);
							}
						}
						set=false;

						count=0; countq2=0;countq3=0;
						if (Qadaug.length()>1)
							Qadaug=Qsort(Qadaug);
						Q1+=Qadaug;
						Qadaug="";
						time++;
						continue;
					}
				
				if (count==q1-1)
				{
					proces_curent.penalty++;
					if(proces_curent.penalty==k)
					{
						Q2+=alias;
						proces_curent.penalty-=k;
						daProces(alias).from=2;
						}
					else
						Qadaug+=alias;
					count=0;
				}
				else count++;
				
				if (Qadaug.length()>1)
					Qadaug=Qsort(Qadaug);
				Q1+=Qadaug;
				Qadaug="";
			}
			else
			{
				if (Q2.compareTo("")!=0||countq2!=0)
				{
					if (countq3<q3&&countq3!=0) {Q3=alias+Q3; countq3=0;}
				
					if (countq2==0)
					{
						alias=Q2.charAt(0);
						proces_curent=daProces(alias);
						Q2=Q2.substring(1);
					}
					

					if (proces_curent.PHASES_COUNT!=proces_curent.faza_curenta)
					{
						proces_curent.phases[proces_curent.faza_curenta].CPU_TIMES_COUNT--;	
					}
					else
					{
						scrie_rand(time,alias,alias2,Q1,Q2,Q3,QIO);
						time++;
						countq2=0;
						proces_curent.finished=true;
						bw.write("<tr><td colspan=7 bgcolor='#ff0000' align='center'>Process "+alias+" is finished.</td></tr>");
						finished=test();
						continue;
					}
					
					scrie_rand(time,alias,alias2,Q1,Q2,Q3,QIO);

					if (proces_IO!=null)
					{
						if (count2<proces_IO.phases[proces_IO.faza_curenta].IO_TIMES_COUNT)
							count2++;
						if (proces_IO.phases[proces_IO.faza_curenta].IO_TIMES_COUNT==count2)
						{
							switch (proces_IO.from)
							{
								case 1:	Q1+=proces_IO.ALIAS; break;
								case 2: Qadaug2+=proces_IO.ALIAS; break;
								case 3: Q3+=proces_IO.ALIAS; break;
							}
							
							if (proces_IO.phases[proces_IO.faza_curenta].REPEAT_COUNT==0)
							{
								proces_IO.faza_curenta++;
								bw.write("<tr><td colspan=7 bgcolor='#ffff00' align='center'>Phase #"+proces_IO.faza_curenta+" of the Process  #"+((int)(alias2)-64)+" is finished.</td></tr>");
							}
							count2=0;
							proces_IO=null;
							alias2='-';
							
							if (QIO.compareTo("")!=0)
							{
								alias2=QIO.charAt(0);
								QIO=QIO.substring(1);
								proces_IO=daProces(alias2);
								set=true;
							}
						}
					}
					
						if (proces_curent.phases[proces_curent.faza_curenta].CPU_TIMES_COUNT==0)
						{
							proces_curent.phases[proces_curent.faza_curenta].REPEAT_COUNT--;
							proces_curent.phases[proces_curent.faza_curenta].CPU_TIMES_COUNT=proces_curent.phases[proces_curent.faza_curenta].cpu_bak;
							proces_curent.award++;

							QIO+=alias;
							daProces(alias).from=2;
							if (proces_curent.award==r)
							{
								proces_curent.award-=r;
								proces_curent.from--;
							}
							
							if (!set)
								{
									if (count2==0)
									{
										alias2=QIO.charAt(0);
										QIO=QIO.substring(1);
										proces_IO=daProces(alias2);
									}
								}
							set=false;
							countq2=0;
							countq3=0;
							if (Qadaug2.length()>1)
								Qadaug2=Qsort(Qadaug2);
							Q2+=Qadaug2;
							Qadaug2="";
							time++;

							continue;
						}
					set=false;
					if (countq2==q2-1)
					{
						proces_curent.penalty++;
						if(proces_curent.penalty==k)
							Q3+=alias;
						else
							Qadaug2+=alias;
						countq2=0;
					
					}
					else countq2++;
					
					if (Qadaug2.length()>1)
						Qadaug2=Qsort(Qadaug2);
					Q2+=Qadaug2;
					Qadaug2="";
				}
				else
				{
					if (Q3.compareTo("")!=0||countq3!=0)
					{
						if (countq3==0)
						{
							alias=Q3.charAt(0);
							proces_curent=daProces(alias);
							Q3=Q3.substring(1);
						}

						if (proces_curent.PHASES_COUNT!=proces_curent.faza_curenta)
						{
							proces_curent.phases[proces_curent.faza_curenta].CPU_TIMES_COUNT--;	
						}
						else
						{
							scrie_rand(time,alias,alias2,Q1,Q2,Q3,QIO);
							time++;
							countq3=0;
							proces_curent.finished=true;
							bw.write("<tr><td colspan=7 bgcolor='#ff0000' align='center'>Process "+alias+" is finished</td></tr>");
							finished=test();
							continue;
						}
							
						scrie_rand(time,alias,alias2,Q1,Q2,Q3,QIO);
						
						if (proces_IO!=null)
						{
							if (count2<proces_IO.phases[proces_IO.faza_curenta].IO_TIMES_COUNT)
								count2++;
							if (proces_IO.phases[proces_IO.faza_curenta].IO_TIMES_COUNT==count2)
							{
								switch (proces_IO.from)
								{
									case 1:	Q1+=proces_IO.ALIAS; break;
									case 2: Q2+=proces_IO.ALIAS; break;
									case 3: Qadaug3+=proces_IO.ALIAS; break;
								}
								
								if (proces_IO.phases[proces_IO.faza_curenta].REPEAT_COUNT==0)
								{
									proces_IO.faza_curenta++;
									bw.write("<tr><td colspan=7 bgcolor='#ffff00' align='center'>Phase #"+proces_IO.faza_curenta+" of the Process #"+((int)(alias2)-64)+" is finished</td></tr>");
								}
								count2=0;
								proces_IO=null;
								alias2='-';
								
								if (QIO.compareTo("")!=0)
									{
										alias2=QIO.charAt(0);
										QIO=QIO.substring(1);
										proces_IO=daProces(alias2);
										set=true;
									}
							}
						}
								
						if (proces_curent.phases[proces_curent.faza_curenta].CPU_TIMES_COUNT==0)
						{
							proces_curent.phases[proces_curent.faza_curenta].REPEAT_COUNT--;
							proces_curent.phases[proces_curent.faza_curenta].CPU_TIMES_COUNT=proces_curent.phases[proces_curent.faza_curenta].cpu_bak;
							proces_curent.award++;

							QIO+=alias;
							daProces(alias).from=3;
							if (proces_curent.award==r)
							{
								proces_curent.award-=r;
								proces_curent.from--;
							}
							
							if (!set)
								{
									if (count2==0)
									{
										alias2=QIO.charAt(0);
										QIO=QIO.substring(1);
										proces_IO=daProces(alias2);
									}
								}
							set=false;
							countq3=0;
							if (Qadaug3.length()>1)
								Qadaug3=Qsort(Qadaug3);
							Q3+=Qadaug3;
							Qadaug3="";
							time++;
							continue;
						}
						set=false;
						if (countq3==q3-1)
						{
							proces_curent.penalty++;
								Qadaug3+=alias;
							countq3=0;
						}
						else countq3++;
						
						if (Qadaug3.length()>1)
							Qadaug3=Qsort(Qadaug3);
						Q3+=Qadaug3;
						Qadaug3="";
					}
					else
					{
						alias='-';
						scrie_rand(time,alias,alias2,Q1,Q3,Q3,QIO);
						
						if (proces_IO!=null)
						{
							if (count2<proces_IO.phases[proces_IO.faza_curenta].IO_TIMES_COUNT)
								count2++;
							if (proces_IO.phases[proces_IO.faza_curenta].IO_TIMES_COUNT==count2)
							{
								switch (proces_IO.from)
								{
									case 1:	Q1+=proces_IO.ALIAS; break;
									case 2: Q2+=proces_IO.ALIAS; break;
									case 3: Q3+=proces_IO.ALIAS; break;
								}
								
								if (proces_IO.phases[proces_IO.faza_curenta].REPEAT_COUNT==0)
								{
									proces_IO.faza_curenta++;
									bw.write("<tr><td colspan=7 bgcolor='#ffff00' align='center'>Phase #"+proces_IO.faza_curenta+" of the Process #"+((int)(alias2)-64)+" </td></tr>");
								}
								
								count2=0;
								proces_IO=null;
								alias2='-';
								
								if (QIO.compareTo("")!=0)
								{
									alias2=QIO.charAt(0);
									QIO=QIO.substring(1);
									proces_IO=daProces(alias2);
								}
							}
						}
					}
				}
				
			}

			time++;
			finished=test();
		}
		bw.write("<tr align='center'><td colspan='7' BGCOLOR='#FF0000'>Simulation is finished.</td></tr>");
	}
	boolean test()
{
	boolean val=true;
	for (int i=0; i<processes_count;i++)
	{
		if (!procese[i].finished)
		{val=false; break;}
	}
	return val;
}



	void scrie_rand(long t, char prCpu, char prIO, String c1, String c2, String c3, String cIO)throws IOException
	{
		String tt="00000"+t;
		tt=tt.substring(tt.length()-6);
		if (c1.compareTo("")==0) c1="-";
		if (c2.compareTo("")==0) c2="-";
		if (c3.compareTo("")==0) c3="-";
		if (cIO.compareTo("")==0) cIO="-";
		
		bw.write("<tr align='center'>");
		bw.write("<td><font color='#0000ff'>"+tt+"</font></td>");
		if (prCpu!='-')
			bw.write("<td><a href=#p"+((int)(prCpu-64))+">"+prCpu+"</a></td>");
		else
			bw.write("<td>"+prCpu+"</td>\n");
			
		if (prIO!='-')
			bw.write("<td><a href=#p"+((int)(prIO-64))+">"+prIO+"</a></td>");
		else
			bw.write("<td>"+prIO+"</td>");
		
		bw.write("<td>"+c1+"</td>");
		bw.write("<td>"+c2+"</td>");
		bw.write("<td>"+c3+"</td>");
		bw.write("<td>"+cIO+"</td>");
		bw.write("</tr> ");
	}
	
	

	Proces daProces(char c)
	{
		Proces a=null;
		for (int ii=0; ii<processes_count;ii++)
		{
			if(procese[ii].ALIAS==c) a=procese[ii];
		}
		return a;
	}

	

	String Qsort(String deOrdonat)
	{	
		char vector[]=new char[10];
		int i=0;
		while (deOrdonat!="")
		{
				vector[i]=deOrdonat.charAt(0);
				if (deOrdonat.length()>1)
					deOrdonat=deOrdonat.substring(1);
				else
					deOrdonat="";
				i++;
		}
		java.util.Arrays.sort(vector,0,i);
		for (int j=0; j<i; j++)
			if (vector[j]!=0)
				deOrdonat+=vector[j];
		return(deOrdonat);
	}

void openDataOutput(){try{
Process pr=Runtime.getRuntime().exec("cmd.exe /C output.htm");
pr.waitFor();
}catch(Exception exception){exception.printStackTrace();}
}	
}
class Phase{
	public int PHASE_COUNT;
	public int CPU_TIMES_COUNT;
	public int IO_TIMES_COUNT;
	public int REPEAT_COUNT;
	public int cpu_bak;
	public Phase(int cpu, int io, int repeat)
	{
		CPU_TIMES_COUNT=cpu;
		IO_TIMES_COUNT=io;
		REPEAT_COUNT=repeat;
		cpu_bak=cpu;
	}
}
class Proces
{
	public String NAME;
	public char ALIAS;
	public int TIME_START;
	public int PHASES_COUNT;
	public Phase []phases;
	public int faza_curenta=0;
	public int penalty=0, award=0;
	private int cpu,io,repeat;
	public int from=0;
	public boolean finished=false;
	public Proces(String nume, char porecla, int start, int nr_faze, Phase []faze)
	{
		NAME=nume;
		ALIAS=porecla;
		TIME_START=start;
		PHASES_COUNT=nr_faze;
		phases=faze;
	}
}