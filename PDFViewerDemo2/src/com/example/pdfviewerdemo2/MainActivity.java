package com.example.pdfviewerdemo2;

import static java.lang.String.format;

import java.io.File;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.ScrollBar;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;

// https://github.com/barteksc/AndroidPdfViewer
public class MainActivity extends Activity {
	private String pdfName = "说明书";
	private String path = "/storage/sdcard0/pdf/pdf.pdf";
	private PDFView pdfView;

	private File file = new File(path);

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		pdfView = (PDFView) findViewById(R.id.pdfView);
		ScrollBar scrollBar = (ScrollBar) findViewById(R.id.scrollBar);
		pdfView.setScrollBar(scrollBar);

		// fromAsset
		// pdfView.fromAsset(pdfName).defaultPage(1).showMinimap(false)
		// .enableSwipe(true).onLoad(new OnLoadCompleteListener() {
		// @Override
		// public void loadComplete(int nbPages) {
		// Toast.makeText(MainActivity.this, "loadComplete",
		// Toast.LENGTH_SHORT).show();
		// }
		// }).onPageChange(new OnPageChangeListener() {
		// @Override
		// public void onPageChanged(int page, int pageCount) {
		// setTitle(format("%s %s / %s", pdfName, page, pageCount));
		// }
		// }).load();
		initData();

	}

	private void initData() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
			boolean isExist= CopyAssetsToSDUtile.fileIsExists(path);
				if(!isExist){
					CopyAssetsToSDUtile.CopyAssets(getApplicationContext(), "pdf", "/storage/sdcard0/pdf");
				}
				mAsyncHandler.sendEmptyMessage(1);
				Log.e("pdf", "send");
			}
		}).start();

	}

	Handler mAsyncHandler = new Handler() {
		@SuppressWarnings("unchecked")
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				loadPdf();
			}
		}
	};

	public void loadPdf() {

		pdfView.fromFile(file).defaultPage(1).showMinimap(false).enableSwipe(true).onLoad(new OnLoadCompleteListener() {
			@Override
			public void loadComplete(int nbPages) {
				Toast.makeText(MainActivity.this, "loadComplete", Toast.LENGTH_SHORT).show();
			}
		}).onPageChange(new OnPageChangeListener() {
			@Override
			public void onPageChanged(int page, int pageCount) {
				setTitle(format("%s %s / %s", pdfName, page, pageCount));
			}
		}).load();
	}
}