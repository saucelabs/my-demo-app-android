package com.saucelabs.mydemoapp.android.utils;

import android.graphics.ImageFormat;
import android.os.Build;
import android.util.Log;

import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.PlanarYUVLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QrCodeAnalyzer implements ImageAnalysis.Analyzer {

    private final List<Integer> yuvFormats;
    private final MultiFormatReader reader;
    private final OnQrCodesDetectedListener onQrCodesDetectedListener;

    public interface OnQrCodesDetectedListener {
        void onQrCodesDetected(Result qrCode);
    }

    public QrCodeAnalyzer(OnQrCodesDetectedListener listener) {
        this.onQrCodesDetectedListener = listener;

        yuvFormats = new ArrayList<>();
        yuvFormats.add(ImageFormat.YUV_420_888);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            yuvFormats.addAll(Arrays.asList(ImageFormat.YUV_422_888, ImageFormat.YUV_444_888));
        }

        reader = new MultiFormatReader();
        Map<DecodeHintType, Object> map = new HashMap<>();
        map.put(DecodeHintType.POSSIBLE_FORMATS, new ArrayList<>(Arrays.asList(com.google.zxing.BarcodeFormat.QR_CODE)));
        reader.setHints(map);
    }

    @Override
    public void analyze(ImageProxy image) {
        if (!yuvFormats.contains(image.getFormat())) {
            Log.e("QRCodeAnalyzer", "Expected YUV, now = " + image.getFormat());
            image.close();
            return;
        } else {
            Log.d("CameraXApp-Eyal", "I am here");
        }

        ByteBuffer buffer = image.getPlanes()[0].getBuffer();
        byte[] data = toByteArray(buffer);

        PlanarYUVLuminanceSource source = new PlanarYUVLuminanceSource(
                data,
                image.getWidth(),
                image.getHeight(),
                0,
                0,
                image.getWidth(),
                image.getHeight(),
                false
        );

        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(source));
        try {
            Result result = reader.decode(binaryBitmap);
            onQrCodesDetectedListener.onQrCodesDetected(result);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        image.close();
    }

    private byte[] toByteArray(ByteBuffer buffer) {
        buffer.rewind();
        byte[] data = new byte[buffer.remaining()];
        buffer.get(data);
        return data;
    }
}
