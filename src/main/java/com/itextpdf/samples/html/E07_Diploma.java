package com.itextpdf.samples.html;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.attach.ITagWorker;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.attach.impl.DefaultTagWorkerFactory;
import com.itextpdf.html2pdf.attach.impl.tags.SpanTagWorker;
import com.itextpdf.html2pdf.html.node.IElementNode;
import com.itextpdf.licensekey.LicenseKey;

public class E07_Diploma {

	public static final String SRC = "src/main/resources/html/tags/diploma.html";
	public static final String DEST = "target/results/html/7_diploma.pdf";
	
    public static void main(String[] args) throws IOException {
        LicenseKey.loadLicenseFile(System.getenv("ITEXT7_LICENSEKEY") + "/itextkey-html2pdf_typography.xml");
    	File file = new File(DEST);
    	file.getParentFile().mkdirs();
        E07_Diploma app = new E07_Diploma();
        app.createPdf(SRC, DEST);
    }
    
    public void createPdf(String src, String dest) throws IOException {
        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setTagWorkerFactory(
        		new DefaultTagWorkerFactory() {
        		    @Override
        		    public ITagWorker getCustomTagWorker(IElementNode tag, ProcessorContext context) {
        		        if ("student".equalsIgnoreCase(tag.name()) ) {
        		            return new SpanTagWorker(tag, context) {
        		            	@Override
        		                public boolean processContent(String content, ProcessorContext context) {
        		                    super.processContent("Bruno Lowagie", context);
        		                    return true;
        		                }
        		            };
        		        }
        		        else if ("date".equalsIgnoreCase(tag.name()) ) {
        		            return new SpanTagWorker(tag, context) {
        		            	@Override
        		                public boolean processContent(String content, ProcessorContext context) {
        		                    super.processContent(new Date().toString(), context);
        		                    return true;
        		                }
        		            };
        		        }
        		        return null;
        		    }
        		}
        	);
        HtmlConverter.convertToPdf(new FileInputStream(src), new FileOutputStream(dest), converterProperties);
    }
}