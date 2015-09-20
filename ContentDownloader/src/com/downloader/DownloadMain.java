package com.downloader;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DownloadMain {
	
	public void downloadFile(String url,String target) throws Exception
	{
		String fname=target+url.substring(url.lastIndexOf("/"));
		URL furl=new URL(url);
		ReadableByteChannel rbc=Channels.newChannel(furl.openStream());
		FileOutputStream fos=new FileOutputStream(fname);
		fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
	}
	
	public boolean download(String web, String ext, String target)
	{
		ext=ext.toLowerCase();
		boolean done=false;
		
		try{
			File tgt=new File(target);
			if(!tgt.exists())//create if dir does not exist
				tgt.mkdirs();
		
			//parse and get location
			Document doc=Jsoup.connect(web).get();
		Elements all=doc.getAllElements();
		for(Element el:all)
		{
			Attributes attrs=el.attributes();
			for(Attribute attr:attrs)
			{
				if(attr.getValue().toLowerCase().endsWith(ext))
					downloadFile(attr.getValue(),target);
			}
		}
		}catch(Exception ex){ex.printStackTrace();}
		
		
		
		
		return done;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DownloadMain obj=new DownloadMain();
		String web="http://istqbexamcertification.com/istqb-dumps-download-mock-tests-and-sample-question-papers-from-2012-to-2014/";
		String ext="pdf";
		String target="/home/ankur/tmp";
		obj.download(web, ext, target);
	}

}
