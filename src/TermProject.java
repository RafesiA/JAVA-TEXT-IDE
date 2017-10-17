/* 
 * cmd.exe를 사용해 java ..\TermProject 명령어로 실행해주세요.
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
	static int CR = 1;							// 전역변수, 1 = Disable Run function, 2 = Enable Run function
	static int Error_count = 0;					// 누적된 에러
	static int Start = 0;						// 추가바람
	String Error_token[] = new String[1000];	// 추가바람
	
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
        System.err.print("에러! 외부 명령 실행에 실패했습니다.\n");
        } if(E_file.exists() == true) {					// 컴파일 오류가 발생 시
			try {
				Scanner e = new Scanner(new FileInputStream(E_file));
				while(e.hasNext()){
    		    	Error_token[Start] = e.next();
    		    	if(Error_token[Start].equals("error:")){				// E_file에 출력되있는 에러 개수 찾기. "error:" 스캔해서 Error_count에 누적
    		    		Error_count++;
    		    	}
    		    }
				System.out.println(Error_count + "개의 에러 발생,컴파일 실패함ㅋ");
				CR = 1;									// 업로드된 소스 코드에 컴파일 오류가 발생시, 전역변수 CR = 1 로 설정하여 Run function Disable
				e.close();
			} catch (FileNotFoundException e1) {
				System.out.println("치명적 오류");
			}
        } 
        else if(E_file.exists() == false) {
        CR = 0;											// 전역변수 CR = 0으로 설정하여 Run function Enable
        System.out.println("컴파일 성공함!");
        }
    }
    	 
    	
    
void Run() {											// 3.Run function
    	if(CR == 0) {									// 컴파일이 오류 없이 성공 했을 때
        String s;
        File file = new File(FileName);
    	String fname = file.getName();
    	int pos = fname.lastIndexOf(".");				// FileName의 확장자 지우기
    	if(pos > 0) {
    		fname = fname.substring(0, pos);
    	}
    	
   	    try {
   	    	Process p = null;
   	    	List<String> cmds = Arrays.asList("java", fname);				// 명령 프롬프트 명령어 집합
   	    	ProcessBuilder oProcess = new ProcessBuilder(cmds);
   	    	p = oProcess.start();
   	    	BufferedReader stdOut = new BufferedReader(new InputStreamReader(p.getInputStream()));
   	    	BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
   	    	while ((s = stdOut.readLine()) != null) System.out.println(s);
   	    	while ((s = stdError.readLine()) != null) System.err.println(s);
   	    	
   	    } catch (IOException e) {
   	      System.err.println("에러! 외부 명령 실행에 실패했습니다.\n" + e.getMessage());
     	}
   	    
    	}
    	else {
    		System.out.println("컴파일 오류, 또는 파일 업로드가 되지 않았습니다.");				// 파일 업로드, 컴파일 오류가 생겼을 때 전역 변수 0
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
    		boolean result = Files.deleteIfExists(E_file.toPath());				// 파일 삭제
    		result = true;
    	} catch (IOException e) {
    		System.out.println("파일 삭제 실패");
    	}
    	
    	FileName = null;
    	System.out.println("초기화되었습니다.");
    	Error_count = 0;											// 누적 에러 수 초기화
    	Start = 0;													// 추가 바람
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