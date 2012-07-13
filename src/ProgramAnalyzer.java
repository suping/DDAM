

import static org.objectweb.asm.Opcodes.ACC_INTERFACE;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.util.TraceClassVisitor;
/**
 * 
 * 
 * @author <a href="mailto:njupsu@gmail.com">Su Ping</a>
 */
public class ProgramAnalyzer {
	
	Hashtable<String, List<String>> coms= new Hashtable<String, List<String>>();
	/**
	 * the given class whether has @Adaptive and @AEjb 
	 * @param cn
	 * @return
	 */
	public boolean isAnalyze(ClassNode cn) {
		
		if (cn.visibleAnnotations != null) {
			Iterator<AnnotationNode> i = cn.visibleAnnotations.iterator();
			while (i.hasNext()) {
				AnnotationNode an = i.next();
				if(an.desc.contains("Ljavax/aejb/Adaptive")){
					//System.out.println(an.desc);
					for (FieldNode fn : (List<FieldNode>) cn.fields) {
						if (fn.visibleAnnotations != null) {
							Iterator<AnnotationNode> fi = fn.visibleAnnotations.iterator();
							while (fi.hasNext()) {
								AnnotationNode fa = fi.next();
								//System.out.println(fa.desc);
								if (fa.desc.contains("AEjb") || fa.desc.contains("Ejb")){
									return true;
								}
							}
						}
					}
				}
			}
			return false;
			
		}
		return false;
	}
	
/**
 * 
 * @param cn
 * the class to analyze and transform
 * 
 */
	public void transform(ClassNode cn) {
		               
				System.out.println("Begin analyzing class:" + cn.name);
				for (MethodNode mn : (List<MethodNode>) cn.methods) {
					MethodAnalyzer methodtransform = new MethodAnalyzer();
//					methodtransform.setCom(findComponents("D:\\program files\\vc-policy-proc-node\\target\\classes\\proc.composite"));
					methodtransform.setCom(findComponents("D:\\program files\\vc-policy-portal-node\\target\\classes\\portal.composite"));
					methodtransform.methodTransform(cn, mn);
					//methodtransform.test(cn, mn);
/*					MAnalyze methodtransform = new MAnalyze();
					methodtransform.methodTransform(cn, mn);*/
					
				}
			
		
	}
	public List<String> findComponents(String fileName)	
	{
		List<String> com = new LinkedList<String>();
		File file = new File(fileName);
		BufferedReader reader = null;
		try {
//		System.out.println("以行为单位读取文件内容，一次读一整行：");
		reader = new BufferedReader(new FileReader(file));
		String tempString = null;
		int line = 1;
		//一次读入一行，直到读入null为文件结束
		while ((tempString = reader.readLine()) != null){
		//显示行号
//		System.out.println("line " + line + ": " + tempString);
		if(tempString.contains("<tuscany:binding.jsonrpc")){			
			com.add(tempString.split("/")[3]+"/"+tempString.split("/")[4].substring(0, tempString.split("/")[4].length()-2));
			System.out.println("com"+tempString.split("/")[3]+"/"+tempString.split("/")[4].substring(0, tempString.split("/")[4].length()-2));
		}
		line++;
		}
		
		reader.close();
		} catch (IOException e) {
		e.printStackTrace();
		} finally {
		if (reader != null){
		try {
		reader.close();
		} catch (IOException e1) {
		}
		}
		}
    	return com;
		
	}
	public void findAllCom(File tempFile){
		if (tempFile.isDirectory()) {
			File file[] = tempFile.listFiles();
			for (int i = 0; i < file.length; i++) {
				System.out.println("Analyze file:" + file[i].getName());
				findAllCom(file[i]);
			}
		} else {
			try {
				if(tempFile.getName().endsWith(".composite")){					
					findComponents(tempFile.getAbsolutePath());
				}				
			} catch (Exception e) {
				e.printStackTrace();

			}
		}
		
	}
	
	public ClassVisitor getClassAdapter(final ClassVisitor cvr) {
		return new ClassNode() {
			@Override
			public void visitEnd() {
				transform(this);
				this.accept(cvr);
			}
		};
	}

	
	/**
	 * find and analyze class files recursively
	 * @param tempFile
	 */
	protected  void begin_analyze(File tempFile) {
		if (tempFile.isDirectory()) {
			File file[] = tempFile.listFiles();
			for (int i = 0; i < file.length; i++) {
				System.out.println("Analyze file:" + file[i].getName());
				begin_analyze(file[i]);
			}
		} else {
			try {				
				if (tempFile.getName().endsWith(".class")) {
					System.out.println(tempFile.getName());
					FileInputStream input = new FileInputStream(tempFile.getAbsolutePath());					
					ClassReader cr = new ClassReader(input);
					ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
					ClassVisitor cv = getClassAdapter(cw);
					cr.accept(cv, 0);

					byte[] b = cw.toByteArray();
					
					FileOutputStream fout = new FileOutputStream(new File(tempFile.getAbsolutePath()));
					fout.write(b);
					fout.close();
				}
			} catch (Exception e) {
				e.printStackTrace();

			}
		}

	}
	
	/**
	 * 鏄剧ず瀛楄妭鐮佹枃浠�
	 * @param file
	 */
		public static void showClassSource(String file) {
			FileInputStream is;
			ClassReader cr;
			try {
				is = new FileInputStream(file);
				cr = new ClassReader(is);
				TraceClassVisitor trace = new TraceClassVisitor(new PrintWriter(
						System.out));
				cr.accept(trace, ClassReader.EXPAND_FRAMES);
				is.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}


	public static void main(String args[]) {
		try {
			
			// unjar the file in a temp file which we are to analyze
/*			ProgramAnalyzer analyse=new ProgramAnalyzer();
			UnjarTool jar2temp = new UnjarTool();
			JarTool  temp2jar = new JarTool();

			String input = args[0];
			String temp = args[1];
			String output = args[2];
//			String input = "D:\\program files\\kitchensink-jsp\\target\\jboss-as-kitchensink-jsp.war";
//			String temp = "e:\\temp";
//			String output = "D:\\program files\\jboss-as-7.1.1.Final\\standalone\\deployments\\jboss-as-kitchensink-jsp.war";
			File tempFile = new File(temp);
			tempFile.mkdir();
			File destJar = new File(output);
			

			jar2temp.unjar(input, temp);			
			analyse.begin_analyze(tempFile);			
			temp2jar.jarDir(tempFile, destJar);*/
		//for debug	
			
//			String filename1="E:\\ServiceAccessing.class";
//			String filename1="D:\\program files\\vc-policy-proc-node\\target\\classes\\services\\ProcServiceImpl.class";
			String filename1="D:\\program files\\vc-policy-portal-node\\target\\classes\\launch\\ServiceAccessing.class";
//			String filename1="C:\\Users\\SuPing\\workspace\\asmtest\\bin\\Ttest.class";
			//String filename1="E:\\ServiceAccessing.class";
			FileInputStream in = new FileInputStream(filename1);
			//showClassSource("D:\\program files\\vc-policy-portal-node\\target\\classes\\launch\\ServiceAccessing.class");
			ProgramAnalyzer t = new ProgramAnalyzer();			
			ClassReader cr = new ClassReader(in);
			ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
			ClassVisitor cv = t.getClassAdapter(cw);
			cr.accept(cv, 0);
			byte[] b = cw.toByteArray();
			
			StringTokenizer filename = new StringTokenizer(filename1, "\\");
			String ss[] = new String[filename.countTokens()];
			int i = 0;
			while (filename.hasMoreTokens()) {
				ss[i++] = filename.nextToken();
			}
			String file = ss[i - 1];
			System.out.println(file);
			File dir = new File("e:\\analize_result");
			dir.mkdir();
			File out = new File("e:\\analize_result\\" + file);

			FileOutputStream fout = new FileOutputStream(out);
			fout.write(b);
			fout.close();

			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
