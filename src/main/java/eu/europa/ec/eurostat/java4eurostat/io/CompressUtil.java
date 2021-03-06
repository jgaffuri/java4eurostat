/**
 * 
 */
package eu.europa.ec.eurostat.java4eurostat.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * @author julien Gaffuri
 *
 */
public class CompressUtil {

	/**
	 * @param inGZIPFile
	 * @param outUnGZIPFile
	 */
	public static void unGZIP(String inGZIPFile, String outUnGZIPFile){
		try {
			GZIPInputStream gzis = new GZIPInputStream(new FileInputStream(inGZIPFile));
			FileOutputStream out = new FileOutputStream(outUnGZIPFile);
			int len;
			byte[] buffer = new byte[1024];
			while ((len = gzis.read(buffer)) > 0)
				out.write(buffer, 0, len);
			gzis.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param inPath
	 * @param zipFile
	 * @param inFiles
	 */
	public static void createZIP(String inPath, String zipFile, String[] inFiles){
		try {
			File outFile = new File(inPath+zipFile);
			if(outFile.exists()) outFile.delete();
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(outFile));
			for(String inFile : inFiles) {
				if(!new File(inPath+inFile).exists()) {
					System.err.println("Could not add file "+inPath+inFile+" to zip: Not found.");
					continue;
				}
				ZipEntry e = new ZipEntry(inFile);
				out.putNextEntry(e);
				byte[] data = Files.readAllBytes(Paths.get(inPath+inFile));
				out.write(data, 0, data.length);
				out.closeEntry();
			}
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	/**
	 * @param zipFile
	 * @param outputFolder
	 */
	public static void unZip(String zipFile, String outputFolder){
		try{
			//output directory
			File folder = new File(outputFolder);
			if(!folder.exists()) folder.mkdir();

			ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
			ZipEntry ze = zis.getNextEntry();
			byte[] buffer = new byte[1024];
			while(ze!=null){
				String fileName = ze.getName();
				File newFile = new File(outputFolder + File.separator + fileName);
				new File(newFile.getParent()).mkdirs();
				FileOutputStream fos = new FileOutputStream(newFile);
				int len;
				while ((len = zis.read(buffer)) > 0) fos.write(buffer, 0, len);
				fos.close();
				ze = zis.getNextEntry();
			}
			zis.closeEntry();
			zis.close();
		}catch(IOException ex){
			ex.printStackTrace();
		}
	}

}
