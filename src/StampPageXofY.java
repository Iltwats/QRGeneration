import com.itextpdf.barcodes.BarcodeQRCode;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.Document;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class StampPageXofY {
    public static final String DEST = "./KarobarQR1601-2000.pdf";
    public static final String SRC = "./src/resources/100.pdf";
    public static String seedNum = "1111113116"; // to make next qr from
    public static List<String> qrData = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        long a = Long.parseLong(seedNum);
        int count = 400;
        for (int i = 1; i <= count; i++) {
            a++;
            new QRMain().addToList(a);
        }
        System.out.println(a);
        new QRMain();
        new QRMain().encodeIt();

        qrData = QRMain.getEncodedText();
        File file = new File(DEST);
        file.getParentFile().mkdirs();

        new StampPageXofY().manipulatePdf(DEST);
    }

    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(SRC), new PdfWriter(dest));
        Document doc = new Document(pdfDoc);
        int count = qrData.size();
        int j = 0;
        int numberOfPages = pdfDoc.getNumberOfPages();
        for (int i = 1; i <= numberOfPages; i++) {
            if (j < count) {
                PdfPage pdfPage = pdfDoc.getPage(i);
                PdfCanvas over = new PdfCanvas(pdfPage);
                /////
                BarcodeQRCode barcodeQRCode = new BarcodeQRCode(qrData.get(j));
                float moduleSize = 200 / barcodeQRCode.getBarcodeSize().getHeight();
                PdfFormXObject barcodeObject = barcodeQRCode.createFormXObject(ColorConstants.BLACK, moduleSize, pdfDoc);
                /////
                BarcodeQRCode barcodeQRCode1 = new BarcodeQRCode(qrData.get(j + 1));
                float moduleSize1 = 200 / barcodeQRCode1.getBarcodeSize().getHeight();
                PdfFormXObject barcodeObject1 = barcodeQRCode1.createFormXObject(ColorConstants.BLACK, moduleSize1, pdfDoc);
                /////
                BarcodeQRCode barcodeQRCode2 = new BarcodeQRCode(qrData.get(j + 2));
                float moduleSize2 = 200 / barcodeQRCode2.getBarcodeSize().getHeight();
                PdfFormXObject barcodeObject2 = barcodeQRCode2.createFormXObject(ColorConstants.BLACK, moduleSize2, pdfDoc);
                /////
                BarcodeQRCode barcodeQRCode3 = new BarcodeQRCode(qrData.get(j + 3));
                float moduleSize3 = 200 / barcodeQRCode3.getBarcodeSize().getHeight();
                PdfFormXObject barcodeObject3 = barcodeQRCode3.createFormXObject(ColorConstants.BLACK, moduleSize3, pdfDoc);

                over.addXObjectAt(barcodeObject, 48, 520);
                over.addXObjectAt(barcodeObject1, 348, 520);
                over.addXObjectAt(barcodeObject2, 48, 100);
                over.addXObjectAt(barcodeObject3, 348, 100);
                j += 4;
            }
        }

        doc.close();
    }
}
