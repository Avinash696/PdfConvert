package com.example.pdfconvert;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.shockwave.pdfium.PdfDocument;

import java.io.File;
import java.util.List;

public class PDFViewActivity extends AppCompatActivity implements OnPageChangeListener, OnLoadCompleteListener {
    PDFView pdfView;
    Integer pageNumber =0;
    String pdfFileName ;
    int position=-1 ;
    String TAG ="rawat";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfview);
        init();
        getSupportActionBar().hide();

    }
    private  void init(){
        pdfView = findViewById(R.id.pdfView);
        position = getIntent().getIntExtra("position",-1);
        displayFromSdCard();
        Log.d(TAG, "init: ");
    }

    private void displayFromSdCard() {
//            pdfFileName = "/sdcard/test.pdf";
            pdfFileName = "/storage/emulated/0/test.pdf";
            File file=new File(pdfFileName);
//        Log.d(TAG, "displayFromSdCard: "+file.getAbsolutePath());
        pdfView.fromFile(file)
                .defaultPage(pageNumber)
                .enableSwipe(true)
                .swipeHorizontal(false)
                .onPageChange(this)
                .enableAnnotationRendering(true)
                .onLoad(this)
                .scrollHandle(new DefaultScrollHandle(this))
                .load();
//        Log.d(TAG, "displayFromSdCard: ");
    }

    @Override
    public void loadComplete(int nbPages) {
//        PdfDocument.Meta meta = pdfView.getDocumentMeta();
//        printBookmarksTree(pdfView.getTableOfContents(), "-");
        //pdf doc from shockwave lib
        PdfDocument.Meta meta = pdfView.getDocumentMeta();
                printBookmarksTree(pdfView.getTableOfContents(), "-");
        Log.d(TAG, "loadComplete: "+meta);
    }

    private void printBookmarksTree(List<PdfDocument.Bookmark> tableOfContents, String s) {
        for(PdfDocument.Bookmark b :tableOfContents)
        Log.d(TAG, "printBookmarksTree: "+s+"/"+b.getTitle());
            PdfDocument.Bookmark tt = new PdfDocument.Bookmark();
        if(tt.hasChildren()){
            Log.d(TAG, "printBookmarksTree: "+tt.getChildren());
        }

    }


    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber =page;
        setTitle(String.format("%s %s / %s ",pdfFileName,page+1,pageCount));
        Log.d(TAG, "onPageChanged: ");
    }
}