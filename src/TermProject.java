/* 
 * cmd.exe�� ����� java ..\TermProject ��ɾ�� �������ּ���.
 */




import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class func {
	String FileName;
	Scanner sc = new Scanner(System.in);
    File E_file = new File("c:\\Temp\\Error_File.txt");
	static int CR = 1;							// ��������, 1 = Disable Run function, 2 = Enable Run function
	static int Error_count = 0;					// ������ ����
	static int Start = 0;						// �߰��ٶ�
	String Error_token[] = new String[1000];	// �߰��ٶ�
	
void Select(){ 										// Selection
        System.out.println();
        System.out.println("-------------------");
        System.out.println("1. Java File Upload");
        System.out.println("2. Compile");
        System.out.println("3. Run");
        System.out.println("4. Reset");
        System.out.println("5. Compile Error List");
        System.out.println("6. End");
        System.out.println("-------------------");
        System.out.print("Choice : ");
        }
    
void uploadJ() {									// 1.Java File Upload
    	System.out.print("Insert File name : ");
    	FileName = sc.next(); 
        }
    

void Compile(){										// 2.Compile

        String s = null;
        File file = new File(FileName);
        String path = file.getAbsolutePath();
        	try {
        		Process oProcess = new ProcessBuilder("javac", path).start();
        		BufferedReader stdError = new BufferedReader(new InputStreamReader(oProcess.getErrorStream()));
        		while ((s = stdError.readLine()) != null) {
        				FileWriter fw = new FileWriter(E_file, true);
        				fw.write(s);
        				fw.flush();
        				fw.close();
        			}
        } catch (IOException e) {
        System.err.print("����! �ܺ� ��� ���࿡ �����߽��ϴ�.\n");
        } if(E_file.exists() == true) {					// ������ ������ �߻� ��
			try {
				Scanner e = new Scanner(new FileInputStream(E_file));
				while(e.hasNext()){
    		    	Error_token[Start] = e.next();
    		    	if(Error_token[Start].equals("error:")){				// E_file�� ��µ��ִ� ���� ���� ã��. "error:" ��ĵ�ؼ� Error_count�� ����
    		    		Error_count++;
    		    	}
    		    }
				System.out.println(Error_count + "���� ���� �߻�,������ �����Ԥ�");
				CR = 1;									// ���ε�� �ҽ� �ڵ忡 ������ ������ �߻���, �������� CR = 1 �� �����Ͽ� Run function Disable
				e.close();
			} catch (FileNotFoundException e1) {
				System.out.println("ġ���� ����");
			}
        } 
        else if(E_file.exists() == false) {
        CR = 0;											// �������� CR = 0���� �����Ͽ� Run function Enable
        System.out.println("������ ������!");
        }
    }
    	 
    	
    
void Run() {											// 3.Run function
    	if(CR == 0) {									// �������� ���� ���� ���� ���� ��
        String s;
        File file = new File(FileName);
    	String fname = file.getName();
    	int pos = fname.lastIndexOf(".");				// FileName�� Ȯ���� �����
    	if(pos > 0) {
    		fname = fname.substring(0, pos);
    	}
    	
   	    try {
   	    	Process p = null;
   	    	List<String> cmds = Arrays.asList("java", fname);				// ��� ������Ʈ ��ɾ� ����
   	    	ProcessBuilder oProcess = new ProcessBuilder(cmds);
   	    	p = oProcess.start();
   	    	BufferedReader stdOut = new BufferedReader(new InputStreamReader(p.getInputStream()));
   	    	BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
   	    	while ((s = stdOut.readLine()) != null) System.out.println(s);
   	    	while ((s = stdError.readLine()) != null) System.err.println(s);
   	    	
   	    } catch (IOException e) {
   	      System.err.println("����! �ܺ� ��� ���࿡ �����߽��ϴ�.\n" + e.getMessage());
     	}
   	    
    	}
    	else {
    		System.out.println("������ ����, �Ǵ� ���� ���ε尡 ���� �ʾҽ��ϴ�.");				// ���� ���ε�, ������ ������ ������ �� ���� ���� 0
    	}
     }
    
void Reset(){																	// 4.Reset function
    	try {
            PrintWriter pw = new PrintWriter(E_file);
            pw.print("");
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    	try {
    		boolean result = Files.deleteIfExists(E_file.toPath());				// ���� ����
    		result = true;
    	} catch (IOException e) {
    		System.out.println("���� ���� ����");
    	}
    	
    	FileName = null;
    	System.out.println("�ʱ�ȭ�Ǿ����ϴ�.");
    	Error_count = 0;											// ���� ���� �� �ʱ�ȭ
    	Start = 0;													// �߰� �ٶ�
    }
    
void Compile_E(){													// 5. Compile Error List
    	
    	FileReader r = null;

    	try {
    		r = new FileReader("C:\\Temp\\Error_File.txt");
    		int c;
    		while((c = r.read()) != -1) {
    			System.out.print((char)c);
    		}
    		
    		r.close();
    		
    	} catch(IOException e){
    		System.out.println("Error!");
    	}
}
void Delete_File() {
	E_file.delete();
}

} // function Class ended
    

public class TermProject {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        func fc = new func();
        while(true){

            fc.Select();
            int s = sc.nextInt();
            switch(s){						// Switch Selection
            case 1:
                fc.uploadJ();
                continue;
            case 2:
                fc.Compile();
                continue;
            case 3:
                fc.Run();
                continue;
            case 4:
                fc.Reset();
                continue;
            case 5:
                fc.Compile_E();
                continue;
            case 6:
            	fc.Delete_File();
                break;
            default:
                System.out.println("Wrong number!");
                continue;
            } 
            
            break;
        }
    }
}