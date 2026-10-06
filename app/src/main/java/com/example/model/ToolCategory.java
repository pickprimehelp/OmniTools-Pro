package com.example.model;

import androidx.compose.ui.graphics.ColorKt;
import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* compiled from: ToolModels.kt */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0011\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B!\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\nR\u0013\u0010\u0005\u001a\u00020\u0006¢\u0006\n\n\u0002\u0010\u000e\u001a\u0004\b\f\u0010\rj\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012j\u0002\b\u0013j\u0002\b\u0014j\u0002\b\u0015j\u0002\b\u0016¨\u0006\u0017"}, d2 = {"Lcom/example/model/ToolCategory;", "", "title", "", "subtitle", "badgeColor", "Landroidx/compose/ui/graphics/Color;", "<init>", "(Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;J)V", "getTitle", "()Ljava/lang/String;", "getSubtitle", "getBadgeColor-0d7_KjU", "()J", "J", "ALL", "DESIGN", "CREATOR", "BUSINESS", "IMAGE", "PDF_DOC", "VOICE", "DAILY", "app"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes9.dex */
public enum ToolCategory {
    ALL("All Tools", "Every tool in one place", ColorKt.Color(4284704497L)),
    DESIGN("Design & Cards", "Weddings, Status, Reels & Thumbnails", ColorKt.Color(4293675161L)),
    CREATOR("Creator Tools", "YouTube, SEO, Hashtags & Revenue", ColorKt.Color(4293870660L)),
    BUSINESS("Business & Finance", "Invoices, GST, EMI & Calculations", ColorKt.Color(4279286145L)),
    IMAGE("Image Tools", "Compress, Resize, Crop & Passport", ColorKt.Color(4282090230L)),
    PDF_DOC("PDF & Document", "PDF Tools, Word Counter & Formatters", ColorKt.Color(4287323382L)),
    VOICE("Voice & Audio", "Voice Changer & Sound Effects", ColorKt.Color(4294286859L)),
    DAILY("Daily Utilities", "QR Code, Barcode, Age, BMI & Time", ColorKt.Color(4279548070L));

    private final long badgeColor;
    private final String subtitle;
    private final String title;
    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries($VALUES);

    public static EnumEntries<ToolCategory> getEntries() {
        return $ENTRIES;
    }

    ToolCategory(String title, String subtitle, long badgeColor) {
        this.title = title;
        this.subtitle = subtitle;
        this.badgeColor = badgeColor;
    }

    /* renamed from: getBadgeColor-0d7_KjU, reason: not valid java name and from getter */
    public final long getBadgeColor() {
        return this.badgeColor;
    }

    public final String getSubtitle() {
        return this.subtitle;
    }

    public final String getTitle() {
        return this.title;
    }
}
