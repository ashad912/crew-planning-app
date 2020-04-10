package pl.jee.klos.service2;


import pl.jee.klos.domain2.CrewMember;
import pl.jee.klos.domain2.Flight;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PdfView extends AbstractITextPdfView {

    private static final DateFormat DATE_FORMAT = DateFormat.getDateInstance(DateFormat.SHORT);

	@Override
	protected void buildPdfDocument(Map<String, Object> model, com.itextpdf.text.Document document,
			com.itextpdf.text.pdf.PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		@SuppressWarnings("unchecked")
        Set<Flight> flights = (Set<Flight>) model.get("flights");
		CrewMember crewMember = (CrewMember) model.get("crewMember");
		
		BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);

		Paragraph preface = new Paragraph(crewMember.getFirstname() + " " + crewMember.getLastname(), new Font(bf, 12)); 
		preface.setAlignment(Element.ALIGN_CENTER);
        document.add(preface);
        document.add( Chunk.NEWLINE );
        
        PdfPTable table = new PdfPTable(5);
      
        table.addCell("Flight ID");
        table.addCell("Start Date");
        table.addCell("End Date");
        table.addCell("Plane");
        table.addCell("Crew Members");
        
        
        for (Flight takenFlight : flights){
        	Set<CrewMember> crewMembers = takenFlight.getCrewMember();
        	String listInOne ="";
        	for(CrewMember takenCrewMember : crewMembers) {
        		listInOne += takenCrewMember.getFirstname() + "\n" + takenCrewMember.getLastname() + ",\n";
        	}
        	listInOne=listInOne.substring(0, listInOne.length()-2);
        	listInOne+=".";
        	
            table.addCell(new Paragraph(String.valueOf(takenFlight.getFlightId()), new Font(bf, 12)));
            
            table.addCell(new Paragraph(DATE_FORMAT.format(takenFlight.getStartDate()), new Font(bf, 12)));
            
            table.addCell(new Paragraph(DATE_FORMAT.format(takenFlight.getEndDate()), new Font(bf, 12)));
            
            table.addCell(new Paragraph(takenFlight.getPlane().getPlaneName(), new Font(bf, 12)));
            
            table.addCell(new Paragraph(listInOne, new Font(bf, 12)));
        }

        document.add(table);
		
	}
}
