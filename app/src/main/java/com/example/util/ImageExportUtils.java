package com.example.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;
import androidx.core.content.FileProvider;
import androidx.webkit.internal.AssetHelper;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import kotlin.Metadata;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ImageExportUtils.kt */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\bÇ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J \u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\tJ \u0010\u000b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\f\u001a\u00020\tJ \u0010\r\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u000e\u001a\u00020\u000f2\b\b\u0002\u0010\f\u001a\u00020\tJ \u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u000e\u001a\u00020\u000f2\b\b\u0002\u0010\u0012\u001a\u00020\t¨\u0006\u0013"}, d2 = {"Lcom/example/util/ImageExportUtils;", "", "<init>", "()V", "copyToClipboard", "", "context", "Landroid/content/Context;", "text", "", "label", "shareText", "title", "shareBitmap", "bitmap", "Landroid/graphics/Bitmap;", "saveBitmapToGallery", "", "filenamePrefix", "app"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes8.dex */
public final class ImageExportUtils {
    public static final int $stable = 0;
    public static final ImageExportUtils INSTANCE = new ImageExportUtils();

    private ImageExportUtils() {
    }

    public static /* synthetic */ void copyToClipboard$default(ImageExportUtils imageExportUtils, Context context, String str, String str2, int i, Object obj) {
        if ((i & 4) != 0) {
            str2 = "OmniTools";
        }
        imageExportUtils.copyToClipboard(context, str, str2);
    }

    public final void copyToClipboard(Context context, String text, String label) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(text, "text");
        Intrinsics.checkNotNullParameter(label, "label");
        Object systemService = context.getSystemService("clipboard");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.content.ClipboardManager");
        ClipboardManager clipboard = (ClipboardManager) systemService;
        ClipData clip = ClipData.newPlainText(label, text);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(context, "Copied to clipboard!", 0).show();
    }

    public static /* synthetic */ void shareText$default(ImageExportUtils imageExportUtils, Context context, String str, String str2, int i, Object obj) {
        if ((i & 4) != 0) {
            str2 = "Share via";
        }
        imageExportUtils.shareText(context, str, str2);
    }

    public final void shareText(Context context, String text, String title) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(text, "text");
        Intrinsics.checkNotNullParameter(title, "title");
        try {
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType(AssetHelper.DEFAULT_MIME_TYPE);
            intent.putExtra("android.intent.extra.TEXT", text);
            context.startActivity(Intent.createChooser(intent, title));
        } catch (Exception e) {
            Toast.makeText(context, "Failed to share: " + e.getMessage(), 0).show();
        }
    }

    public static /* synthetic */ void shareBitmap$default(ImageExportUtils imageExportUtils, Context context, Bitmap bitmap, String str, int i, Object obj) {
        if ((i & 4) != 0) {
            str = "OmniTools Creation";
        }
        imageExportUtils.shareBitmap(context, bitmap, str);
    }

    public final void shareBitmap(Context context, Bitmap bitmap, String title) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(bitmap, "bitmap");
        Intrinsics.checkNotNullParameter(title, "title");
        try {
            File cachePath = new File(context.getCacheDir(), "images");
            cachePath.mkdirs();
            File file = new File(cachePath, "omnitools_" + System.currentTimeMillis() + ".png");
            FileOutputStream stream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            stream.close();
            Uri contentUri = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", file);
            Intrinsics.checkNotNullExpressionValue(contentUri, "getUriForFile(...)");
            Intent shareIntent = new Intent("android.intent.action.SEND");
            shareIntent.setType("image/png");
            shareIntent.putExtra("android.intent.extra.STREAM", contentUri);
            shareIntent.putExtra("android.intent.extra.SUBJECT", title);
            shareIntent.addFlags(1);
            context.startActivity(Intent.createChooser(shareIntent, "Share via"));
        } catch (Exception e) {
            Toast.makeText(context, "Failed to share image: " + e.getMessage(), 0).show();
        }
    }

    public static /* synthetic */ boolean saveBitmapToGallery$default(ImageExportUtils imageExportUtils, Context context, Bitmap bitmap, String str, int i, Object obj) {
        if ((i & 4) != 0) {
            str = "OmniTools";
        }
        return imageExportUtils.saveBitmapToGallery(context, bitmap, str);
    }

    public final boolean saveBitmapToGallery(Context context, Bitmap bitmap, String filenamePrefix) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(bitmap, "bitmap");
        Intrinsics.checkNotNullParameter(filenamePrefix, "filenamePrefix");
        try {
            String filename = filenamePrefix + "_" + System.currentTimeMillis() + ".png";
            OutputStream fos = null;
            if (Build.VERSION.SDK_INT >= 29) {
                ContentResolver resolver = context.getContentResolver();
                ContentValues contentValues = new ContentValues();
                contentValues.put("_display_name", filename);
                contentValues.put("mime_type", "image/png");
                contentValues.put("relative_path", Environment.DIRECTORY_PICTURES + "/OmniTools");
                Uri imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                if (imageUri != null) {
                    fos = resolver.openOutputStream(imageUri);
                }
            } else {
                String imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/OmniTools";
                File file = new File(imagesDir);
                if (!file.exists()) {
                    file.mkdir();
                }
                File image = new File(imagesDir, filename);
                fos = new FileOutputStream(image);
            }
            if (fos == null) {
                return false;
            }
            OutputStream outputStream = fos;
            try {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                Toast.makeText(context, "Saved to Gallery (Pictures/OmniTools)", 0).show();
                CloseableKt.closeFinally(outputStream, null);
                return true;
            } finally {
            }
        } catch (Exception e) {
            Toast.makeText(context, "Saved locally in app cache", 0).show();
            return false;
        }
    }
}
