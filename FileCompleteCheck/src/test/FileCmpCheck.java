/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;


/**
 *
 * @author 赵学鹏
 */
public class FileCmpCheck {
     static String Scriptdir;
    public static void main(String[] args) throws IOException {
            
               run();
               
//                File f = new File("D:\\GS_AutoTest_VS_V60\\TestObjects\\QYZJ\\TR.rftmap");
//                System.out.println(f.exists());
                
    }
    
    static void run() throws FileNotFoundException, IOException{
    
     System.out.println("输入脚本路径");
        Scanner s = new Scanner(System.in);
        Scriptdir = s.nextLine();
        String actionDir = Scriptdir+"\\Script_Action";
        String caseDir = Scriptdir+"\\Script_Case";
        String ExecDir = Scriptdir+"\\Script_Exec";
        //StringBuilder sb = new StringBuilder();
        check(actionDir);
        check(caseDir);
        check(ExecDir);
        System.out.println("检查完成");
    }

 static void check(String actionDir) throws FileNotFoundException, IOException{
        String typeDir;
        if(actionDir.contains("Script_Action")){
            typeDir = "Script_Action";
        }else if(actionDir.contains("Script_Case")){
            typeDir = "Script_Case";
        }else if(actionDir.contains("Script_Exec")){
             typeDir = "Script_Exec";
        }else{
            System.out.println("程序有异常 请检查！");
            return;
        }
        File actionDirFile = new File(actionDir);
        //System.err.println("actionDir="+actionDir);
        if(actionDirFile.exists() && actionDirFile.isDirectory()){
            ArrayList<String> vbList = tools.getFiles(actionDirFile, "vb");
            Iterator<String> it = vbList.iterator();
            while (it.hasNext()) {
                //获取每个vb文件的绝对路径 vbPath
                String vbPath = it.next();
                //获取vb的纯文件名 vbfileName
                String vbfileName = vbPath.substring(vbPath.lastIndexOf("\\")+1,vbPath.length()-4);
                //根据拼接字符传去找resource下对应rftdef和helper.vb文件
                String x=vbPath.substring(0,vbPath.indexOf(typeDir))+"resources\\"+vbPath.substring(vbPath.indexOf(typeDir),vbPath.lastIndexOf("."));
                String rftdefDir = x+".rftdef";
                String helperDir = x+"Helper.vb";
                String mapDir = "";
                boolean flag = true;
                if(!new File(helperDir).exists()){
                    
                    System.err.println((helperDir+"不存在，请检查！"));
                }
                if(!new File(rftdefDir).exists()){
                     flag = false;
                     System.err.println((rftdefDir+"不存在，请检查！"));
                } else{
                     FileReader defRead = new FileReader((new File(rftdefDir)));
                     
                   
                    int num=0;
                    char[] arr = new char[1024];
                    while((num=(defRead.read(arr)))!=-1){
                    String z = String.valueOf(arr);
                    //System.out.println(z);
                    if(z.contains("<Map>")){
                             //未调试，需确认
                            //mapDir =String.valueOf(arr).substring(String.valueOf(arr).indexOf("<Map>")+5,String.valueOf(arr).indexOf("</Map>")).replace('/', '\\');
                             
                            mapDir =Scriptdir+"\\"+z.substring(z.indexOf("<Map>")+5,z.indexOf("</Map>")).replace('/', '\\');
                            //System.err.println("mapDir = "+mapDir);
                            break;
                         }
		}
                    }
                    if(!new File(mapDir).exists() && flag){
                        //sb.append(s+"\\"+mapDir+"不存在，请检查！"+"\r\n");
                         System.err.println((vbPath+"所引用对象图："+mapDir+"不存在，请检查！"));
                    }
                }
            
        }else{
            
            System.err.println("路径有误");
        }
        
 }  
}