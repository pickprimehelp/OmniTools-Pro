package com.example.ui.tools.pdf;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.widget.Toast;
import androidx.activity.compose.BackHandlerKt;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnScope;
import androidx.compose.foundation.layout.ColumnScopeInstance;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.PaddingValues;
import androidx.compose.material3.IconButtonKt;
import androidx.compose.material3.MaterialTheme;
import androidx.compose.material3.ScaffoldKt;
import androidx.compose.material3.TextKt;
import androidx.compose.runtime.Applier;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalMap;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.ProvidableCompositionLocal;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.ScopeUpdateScope;
import androidx.compose.runtime.SnapshotStateKt__SnapshotStateKt;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.text.TextLayoutResult;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.font.FontFamily;
import androidx.compose.ui.text.font.FontStyle;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.TextAlign;
import androidx.compose.ui.text.style.TextDecoration;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.TextUnitKt;
import androidx.core.content.FileProvider;
import androidx.core.view.ViewCompat;
import androidx.profileinstaller.ProfileVerifier;
import com.example.util.ImageExportUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt;

/* compiled from: PdfTools.kt */
@Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0004\u001a\u001b\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003H\u0007¢\u0006\u0002\u0010\u0004\u001a\u001b\u0010\u0005\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003H\u0007¢\u0006\u0002\u0010\u0004\u001a\u001b\u0010\u0006\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003H\u0007¢\u0006\u0002\u0010\u0004¨\u0006\u0007²\u0006\n\u0010\b\u001a\u00020\tX\u008a\u008e\u0002²\u0006\n\u0010\n\u001a\u00020\tX\u008a\u008e\u0002²\u0006\n\u0010\u000b\u001a\u00020\tX\u008a\u008e\u0002²\u0006\n\u0010\f\u001a\u00020\tX\u008a\u008e\u0002²\u0006\n\u0010\r\u001a\u00020\tX\u008a\u008e\u0002"}, d2 = {"TextToPdfScreen", "", "onBack", "Lkotlin/Function0;", "(Lkotlin/jvm/functions/Function0;Landroidx/compose/runtime/Composer;I)V", "WordAndCharacterCounterScreen", "CaseConverterScreen", "app", "docTitle", "", "docAuthor", "docContent", "textInput", "inputText"}, k = 2, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class PdfToolsKt {
    /* JADX INFO: Access modifiers changed from: package-private */
    public static final Unit CaseConverterScreen$lambda$85(Function0 function0, int i, Composer composer, int i2) {
        CaseConverterScreen(function0, composer, RecomposeScopeImplKt.updateChangedFlags(i | 1));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static final Unit TextToPdfScreen$lambda$29(Function0 function0, int i, Composer composer, int i2) {
        TextToPdfScreen(function0, composer, RecomposeScopeImplKt.updateChangedFlags(i | 1));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static final Unit WordAndCharacterCounterScreen$lambda$59(Function0 function0, int i, Composer composer, int i2) {
        WordAndCharacterCounterScreen(function0, composer, RecomposeScopeImplKt.updateChangedFlags(i | 1));
        return Unit.INSTANCE;
    }

    public static final void TextToPdfScreen(final Function0<Unit> onBack, Composer $composer, final int $changed) {
        Object obj;
        Object obj2;
        Object obj3;
        Object obj4;
        Intrinsics.checkNotNullParameter(onBack, "onBack");
        Composer $composer2 = $composer.startRestartGroup(1630739099);
        ComposerKt.sourceInformation($composer2, "C(TextToPdfScreen)78@3364L12,78@3352L24,79@3408L7,81@3437L61,82@3520L47,83@3590L416,158@6499L536,172@7042L1851,157@6472L2421:PdfTools.kt#ioq88a");
        int $dirty = $changed;
        if (($changed & 6) == 0) {
            $dirty |= $composer2.changedInstance(onBack) ? 4 : 2;
        }
        if (($dirty & 3) == 2 && $composer2.getSkipping()) {
            $composer2.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(1630739099, $dirty, -1, "com.example.ui.tools.pdf.TextToPdfScreen (PdfTools.kt:77)");
            }
            ComposerKt.sourceInformationMarkerStart($composer2, -522785369, "CC(remember):PdfTools.kt#9igjgp");
            boolean z = ($dirty & 14) == 4;
            Object rememberedValue = $composer2.rememberedValue();
            if (z || rememberedValue == Composer.INSTANCE.getEmpty()) {
                obj = new Function0() { // from class: com.example.ui.tools.pdf.PdfToolsKt$$ExternalSyntheticLambda17
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return PdfToolsKt.TextToPdfScreen$lambda$1$lambda$0(Function0.this);
                    }
                };
                $composer2.updateRememberedValue(obj);
            } else {
                obj = rememberedValue;
            }
            ComposerKt.sourceInformationMarkerEnd($composer2);
            BackHandlerKt.BackHandler(false, (Function0) obj, $composer2, 0, 1);
            ProvidableCompositionLocal<Context> localContext = AndroidCompositionLocals_androidKt.getLocalContext();
            ComposerKt.sourceInformationMarkerStart($composer2, 2023513938, "CC:CompositionLocal.kt#9igjgp");
            Object consume = $composer2.consume(localContext);
            ComposerKt.sourceInformationMarkerEnd($composer2);
            final Context context = (Context) consume;
            ComposerKt.sourceInformationMarkerStart($composer2, -522782984, "CC(remember):PdfTools.kt#9igjgp");
            Object rememberedValue2 = $composer2.rememberedValue();
            if (rememberedValue2 == Composer.INSTANCE.getEmpty()) {
                obj2 = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default("Project Specification Document", null, 2, null);
                $composer2.updateRememberedValue(obj2);
            } else {
                obj2 = rememberedValue2;
            }
            final MutableState docTitle$delegate = (MutableState) obj2;
            ComposerKt.sourceInformationMarkerEnd($composer2);
            ComposerKt.sourceInformationMarkerStart($composer2, -522780342, "CC(remember):PdfTools.kt#9igjgp");
            Object rememberedValue3 = $composer2.rememberedValue();
            if (rememberedValue3 == Composer.INSTANCE.getEmpty()) {
                obj3 = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default("OmniTools Author", null, 2, null);
                $composer2.updateRememberedValue(obj3);
            } else {
                obj3 = rememberedValue3;
            }
            final MutableState docAuthor$delegate = (MutableState) obj3;
            ComposerKt.sourceInformationMarkerEnd($composer2);
            ComposerKt.sourceInformationMarkerStart($composer2, -522777733, "CC(remember):PdfTools.kt#9igjgp");
            Object rememberedValue4 = $composer2.rememberedValue();
            if (rememberedValue4 == Composer.INSTANCE.getEmpty()) {
                obj4 = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default("This document was created directly using OmniTools.\n\nKey Features:\n• Clean PDF formatting and pagination\n• Standard A4 document canvas (595 x 842 points)\n• Instant PDF export and WhatsApp/Email sharing\n\nThank you for choosing OmniTools All-in-One Utility Kit!", null, 2, null);
                $composer2.updateRememberedValue(obj4);
            } else {
                obj4 = rememberedValue4;
            }
            final MutableState docContent$delegate = (MutableState) obj4;
            ComposerKt.sourceInformationMarkerEnd($composer2);
            ScaffoldKt.m2411ScaffoldTvnljyQ(null, ComposableLambdaKt.rememberComposableLambda(2096629079, true, new Function2() { // from class: com.example.ui.tools.pdf.PdfToolsKt$$ExternalSyntheticLambda18
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj5, Object obj6) {
                    return PdfToolsKt.TextToPdfScreen$lambda$15(Function0.this, (Composer) obj5, ((Integer) obj6).intValue());
                }
            }, $composer2, 54), null, null, null, 0, 0L, 0L, null, ComposableLambdaKt.rememberComposableLambda(1211727660, true, new Function3() { // from class: com.example.ui.tools.pdf.PdfToolsKt$$ExternalSyntheticLambda19
                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj5, Object obj6, Object obj7) {
                    return PdfToolsKt.TextToPdfScreen$lambda$28(MutableState.this, docAuthor$delegate, docContent$delegate, context, (PaddingValues) obj5, (Composer) obj6, ((Integer) obj7).intValue());
                }
            }, $composer2, 54), $composer2, 805306416, 509);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        ScopeUpdateScope endRestartGroup = $composer2.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.updateScope(new Function2() { // from class: com.example.ui.tools.pdf.PdfToolsKt$$ExternalSyntheticLambda20
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj5, Object obj6) {
                    return PdfToolsKt.TextToPdfScreen$lambda$29(Function0.this, $changed, (Composer) obj5, ((Integer) obj6).intValue());
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static final Unit TextToPdfScreen$lambda$1$lambda$0(Function0 $onBack) {
        $onBack.invoke();
        return Unit.INSTANCE;
    }

    private static final String TextToPdfScreen$lambda$3(MutableState<String> mutableState) {
        return mutableState.getValue();
    }

    private static final String TextToPdfScreen$lambda$6(MutableState<String> mutableState) {
        return mutableState.getValue();
    }

    private static final String TextToPdfScreen$lambda$9(MutableState<String> mutableState) {
        return mutableState.getValue();
    }

    private static final void TextToPdfScreen$generateAndSharePdf(Context context, String title, String author, String content, boolean isShare) {
        try {
            PdfDocument pdfDoc = new PdfDocument();
            PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(595, 842, 1).create();
            PdfDocument.Page page = pdfDoc.startPage(pageInfo);
            Canvas canvas = page.getCanvas();
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(ViewCompat.MEASURED_STATE_MASK);
            paint.setTextSize(24.0f);
            paint.setFakeBoldText(true);
            canvas.drawText(title, 40.0f, 60.0f, paint);
            paint.setTextSize(12.0f);
            paint.setFakeBoldText(false);
            paint.setColor(-12303292);
            canvas.drawText("Author: " + author + "  |  Generated by OmniTools", 40.0f, 85.0f, paint);
            paint.setColor(-3355444);
            paint.setStrokeWidth(1.0f);
            canvas.drawLine(40.0f, 100.0f, 555.0f, 100.0f, paint);
            paint.setColor(ViewCompat.MEASURED_STATE_MASK);
            paint.setTextSize(13.0f);
            float currentY = 130.0f;
            List<String> lines = StringsKt.split$default((CharSequence) content, new String[]{"\n"}, false, 0, 6, (Object) null);
            for (String line : lines) {
                if (currentY > 800.0f) {
                    break;
                }
                canvas.drawText(line, 40.0f, currentY, paint);
                currentY += 20.0f;
            }
            pdfDoc.finishPage(page);
            File file = new File(context.getCacheDir(), "OmniTools_Doc_" + System.currentTimeMillis() + ".pdf");
            FileOutputStream outputStream = new FileOutputStream(file);
            pdfDoc.writeTo(outputStream);
            pdfDoc.close();
            outputStream.close();
            Uri uri = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", file);
            Intrinsics.checkNotNullExpressionValue(uri, "getUriForFile(...)");
            if (!isShare) {
                Toast.makeText(context, "PDF saved to app documents cache!", 0).show();
                return;
            }
            Intent shareIntent = new Intent("android.intent.action.SEND");
            shareIntent.setType("application/pdf");
            shareIntent.putExtra("android.intent.extra.STREAM", uri);
            shareIntent.putExtra("android.intent.extra.SUBJECT", title);
            shareIntent.addFlags(1);
            context.startActivity(Intent.createChooser(shareIntent, "Share PDF"));
        } catch (Exception e) {
            Toast.makeText(context, "Error generating PDF: " + e.getMessage(), 0).show();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Removed duplicated region for block: B:25:0x01ab  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final kotlin.Unit TextToPdfScreen$lambda$15(final kotlin.jvm.functions.Function0 r41, androidx.compose.runtime.Composer r42, int r43) {
        /*
            Method dump skipped, instructions count: 433
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.example.ui.tools.pdf.PdfToolsKt.TextToPdfScreen$lambda$15(kotlin.jvm.functions.Function0, androidx.compose.runtime.Composer, int):kotlin.Unit");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static final Unit TextToPdfScreen$lambda$15$lambda$14$lambda$13(Function0 $onBack, Composer $composer, int $changed) {
        ComposerKt.sourceInformation($composer, "C163@6670L155:PdfTools.kt#ioq88a");
        if (($changed & 3) == 2 && $composer.getSkipping()) {
            $composer.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(-1062890521, $changed, -1, "com.example.ui.tools.pdf.TextToPdfScreen.<anonymous>.<anonymous>.<anonymous> (PdfTools.kt:163)");
            }
            IconButtonKt.IconButton($onBack, null, false, null, null, ComposableSingletons$PdfToolsKt.INSTANCE.getLambda$1535675716$app(), $composer, ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_CANT_WRITE_PROFILE_VERIFICATION_RESULT_CACHE_FILE, 30);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Removed duplicated region for block: B:32:0x01bd  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x023b  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x02b7  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x038b  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0397  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x044c  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x04b8  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0543  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x04c6  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x045a  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x039d  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x02c5  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x0249  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x01cb  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final kotlin.Unit TextToPdfScreen$lambda$28(final androidx.compose.runtime.MutableState r68, final androidx.compose.runtime.MutableState r69, final androidx.compose.runtime.MutableState r70, final android.content.Context r71, androidx.compose.foundation.layout.PaddingValues r72, androidx.compose.runtime.Composer r73, int r74) {
        /*
            Method dump skipped, instructions count: 1353
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.example.ui.tools.pdf.PdfToolsKt.TextToPdfScreen$lambda$28(androidx.compose.runtime.MutableState, androidx.compose.runtime.MutableState, androidx.compose.runtime.MutableState, android.content.Context, androidx.compose.foundation.layout.PaddingValues, androidx.compose.runtime.Composer, int):kotlin.Unit");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static final Unit TextToPdfScreen$lambda$28$lambda$27$lambda$17$lambda$16(MutableState $docTitle$delegate, String it) {
        Intrinsics.checkNotNullParameter(it, "it");
        $docTitle$delegate.setValue(it);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static final Unit TextToPdfScreen$lambda$28$lambda$27$lambda$19$lambda$18(MutableState $docAuthor$delegate, String it) {
        Intrinsics.checkNotNullParameter(it, "it");
        $docAuthor$delegate.setValue(it);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static final Unit TextToPdfScreen$lambda$28$lambda$27$lambda$21$lambda$20(MutableState $docContent$delegate, String it) {
        Intrinsics.checkNotNullParameter(it, "it");
        $docContent$delegate.setValue(it);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static final Unit TextToPdfScreen$lambda$28$lambda$27$lambda$26$lambda$23$lambda$22(MutableState $docTitle$delegate, MutableState $docAuthor$delegate, MutableState $docContent$delegate, Context $context) {
        TextToPdfScreen$generateAndSharePdf($context, TextToPdfScreen$lambda$3($docTitle$delegate), TextToPdfScreen$lambda$6($docAuthor$delegate), TextToPdfScreen$lambda$9($docContent$delegate), false);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static final Unit TextToPdfScreen$lambda$28$lambda$27$lambda$26$lambda$25$lambda$24(MutableState $docTitle$delegate, MutableState $docAuthor$delegate, MutableState $docContent$delegate, Context $context) {
        TextToPdfScreen$generateAndSharePdf($context, TextToPdfScreen$lambda$3($docTitle$delegate), TextToPdfScreen$lambda$6($docAuthor$delegate), TextToPdfScreen$lambda$9($docContent$delegate), true);
        return Unit.INSTANCE;
    }

    public static final void WordAndCharacterCounterScreen(final Function0<Unit> onBack, Composer $composer, final int $changed) {
        Object obj;
        Object obj2;
        Intrinsics.checkNotNullParameter(onBack, "onBack");
        Composer $composer2 = $composer.startRestartGroup(572410501);
        ComposerKt.sourceInformation($composer2, "C(WordAndCharacterCounterScreen)220@9145L12,220@9133L24,221@9189L7,223@9219L121,237@9944L544,251@10495L4519,236@9917L5097:PdfTools.kt#ioq88a");
        int $dirty = $changed;
        if (($changed & 6) == 0) {
            $dirty |= $composer2.changedInstance(onBack) ? 4 : 2;
        }
        if (($dirty & 3) == 2 && $composer2.getSkipping()) {
            $composer2.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(572410501, $dirty, -1, "com.example.ui.tools.pdf.WordAndCharacterCounterScreen (PdfTools.kt:219)");
            }
            ComposerKt.sourceInformationMarkerStart($composer2, 330681553, "CC(remember):PdfTools.kt#9igjgp");
            boolean z = ($dirty & 14) == 4;
            Object rememberedValue = $composer2.rememberedValue();
            if (z || rememberedValue == Composer.INSTANCE.getEmpty()) {
                obj = new Function0() { // from class: com.example.ui.tools.pdf.PdfToolsKt$$ExternalSyntheticLambda1
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return PdfToolsKt.WordAndCharacterCounterScreen$lambda$31$lambda$30(Function0.this);
                    }
                };
                $composer2.updateRememberedValue(obj);
            } else {
                obj = rememberedValue;
            }
            ComposerKt.sourceInformationMarkerEnd($composer2);
            BackHandlerKt.BackHandler(false, (Function0) obj, $composer2, 0, 1);
            ProvidableCompositionLocal<Context> localContext = AndroidCompositionLocals_androidKt.getLocalContext();
            ComposerKt.sourceInformationMarkerStart($composer2, 2023513938, "CC:CompositionLocal.kt#9igjgp");
            Object consume = $composer2.consume(localContext);
            ComposerKt.sourceInformationMarkerEnd($composer2);
            ComposerKt.sourceInformationMarkerStart($composer2, 330684030, "CC(remember):PdfTools.kt#9igjgp");
            Object rememberedValue2 = $composer2.rememberedValue();
            if (rememberedValue2 == Composer.INSTANCE.getEmpty()) {
                obj2 = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default("The quick brown fox jumps over the lazy dog. OmniTools makes productivity simple and fast!", null, 2, null);
                $composer2.updateRememberedValue(obj2);
            } else {
                obj2 = rememberedValue2;
            }
            final MutableState textInput$delegate = (MutableState) obj2;
            ComposerKt.sourceInformationMarkerEnd($composer2);
            final int charCount = WordAndCharacterCounterScreen$lambda$33(textInput$delegate).length();
            final int charNoSpaces = StringsKt.replace$default(StringsKt.replace$default(WordAndCharacterCounterScreen$lambda$33(textInput$delegate), " ", "", false, 4, (Object) null), "\n", "", false, 4, (Object) null).length();
            Iterable split = new Regex("\\s+").split(StringsKt.trim((CharSequence) WordAndCharacterCounterScreen$lambda$33(textInput$delegate)).toString(), 0);
            Collection arrayList = new ArrayList();
            for (Object obj3 : split) {
                if (!StringsKt.isBlank((String) obj3)) {
                    arrayList.add(obj3);
                }
            }
            List words = (List) arrayList;
            final int wordCount = words.size();
            Iterable split2 = new Regex("[.!?]+").split(WordAndCharacterCounterScreen$lambda$33(textInput$delegate), 0);
            Collection arrayList2 = new ArrayList();
            for (Object obj4 : split2) {
                if (!StringsKt.isBlank((String) obj4)) {
                    arrayList2.add(obj4);
                }
            }
            final int sentenceCount = ((List) arrayList2).size();
            Iterable split$default = StringsKt.split$default((CharSequence) WordAndCharacterCounterScreen$lambda$33(textInput$delegate), new String[]{"\n\n"}, false, 0, 6, (Object) null);
            Collection arrayList3 = new ArrayList();
            for (Object obj5 : split$default) {
                if (!StringsKt.isBlank((String) obj5)) {
                    arrayList3.add(obj5);
                }
            }
            final int paragraphCount = ((List) arrayList3).size();
            final int readingTimeSec = (wordCount * 60) / 200;
            final int speakingTimeSec = (wordCount * 60) / 130;
            ScaffoldKt.m2411ScaffoldTvnljyQ(null, ComposableLambdaKt.rememberComposableLambda(-484197311, true, new Function2() { // from class: com.example.ui.tools.pdf.PdfToolsKt$$ExternalSyntheticLambda2
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj6, Object obj7) {
                    return PdfToolsKt.WordAndCharacterCounterScreen$lambda$40(Function0.this, (Composer) obj6, ((Integer) obj7).intValue());
                }
            }, $composer2, 54), null, null, null, 0, 0L, 0L, null, ComposableLambdaKt.rememberComposableLambda(-512959146, true, new Function3() { // from class: com.example.ui.tools.pdf.PdfToolsKt$$ExternalSyntheticLambda3
                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj6, Object obj7, Object obj8) {
                    return PdfToolsKt.WordAndCharacterCounterScreen$lambda$58(wordCount, charCount, textInput$delegate, charNoSpaces, sentenceCount, paragraphCount, readingTimeSec, speakingTimeSec, (PaddingValues) obj6, (Composer) obj7, ((Integer) obj8).intValue());
                }
            }, $composer2, 54), $composer2, 805306416, 509);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        ScopeUpdateScope endRestartGroup = $composer2.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.updateScope(new Function2() { // from class: com.example.ui.tools.pdf.PdfToolsKt$$ExternalSyntheticLambda4
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj6, Object obj7) {
                    return PdfToolsKt.WordAndCharacterCounterScreen$lambda$59(Function0.this, $changed, (Composer) obj6, ((Integer) obj7).intValue());
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static final Unit WordAndCharacterCounterScreen$lambda$31$lambda$30(Function0 $onBack) {
        $onBack.invoke();
        return Unit.INSTANCE;
    }

    private static final String WordAndCharacterCounterScreen$lambda$33(MutableState<String> mutableState) {
        return mutableState.getValue();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Removed duplicated region for block: B:25:0x01ab  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final kotlin.Unit WordAndCharacterCounterScreen$lambda$40(final kotlin.jvm.functions.Function0 r41, androidx.compose.runtime.Composer r42, int r43) {
        /*
            Method dump skipped, instructions count: 433
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.example.ui.tools.pdf.PdfToolsKt.WordAndCharacterCounterScreen$lambda$40(kotlin.jvm.functions.Function0, androidx.compose.runtime.Composer, int):kotlin.Unit");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static final Unit WordAndCharacterCounterScreen$lambda$40$lambda$39$lambda$38(Function0 $onBack, Composer $composer, int $changed) {
        ComposerKt.sourceInformation($composer, "C242@10123L155:PdfTools.kt#ioq88a");
        if (($changed & 3) == 2 && $composer.getSkipping()) {
            $composer.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(-194212143, $changed, -1, "com.example.ui.tools.pdf.WordAndCharacterCounterScreen.<anonymous>.<anonymous>.<anonymous> (PdfTools.kt:242)");
            }
            IconButtonKt.IconButton($onBack, null, false, null, null, ComposableSingletons$PdfToolsKt.INSTANCE.m7157getLambda$354590354$app(), $composer, ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_CANT_WRITE_PROFILE_VERIFICATION_RESULT_CACHE_FILE, 30);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Removed duplicated region for block: B:32:0x020f  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x021b  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x03a2  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x04d1  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x03b2  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0221  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final kotlin.Unit WordAndCharacterCounterScreen$lambda$58(final int r63, final int r64, final androidx.compose.runtime.MutableState r65, final int r66, final int r67, final int r68, final int r69, final int r70, androidx.compose.foundation.layout.PaddingValues r71, androidx.compose.runtime.Composer r72, int r73) {
        /*
            Method dump skipped, instructions count: 1239
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.example.ui.tools.pdf.PdfToolsKt.WordAndCharacterCounterScreen$lambda$58(int, int, androidx.compose.runtime.MutableState, int, int, int, int, int, androidx.compose.foundation.layout.PaddingValues, androidx.compose.runtime.Composer, int):kotlin.Unit");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static final Unit WordAndCharacterCounterScreen$lambda$58$lambda$57$lambda$45$lambda$42(int $wordCount, ColumnScope Card, Composer $composer, int $changed) {
        Intrinsics.checkNotNullParameter(Card, "$this$Card");
        ComposerKt.sourceInformation($composer, "C266@11148L308:PdfTools.kt#ioq88a");
        if (($changed & 17) == 16 && $composer.getSkipping()) {
            $composer.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(356848162, $changed, -1, "com.example.ui.tools.pdf.WordAndCharacterCounterScreen.<anonymous>.<anonymous>.<anonymous>.<anonymous> (PdfTools.kt:266)");
            }
            Modifier m673padding3ABfNKs = PaddingKt.m673padding3ABfNKs(Modifier.INSTANCE, Dp.m6625constructorimpl(14));
            Alignment.Horizontal centerHorizontally = Alignment.INSTANCE.getCenterHorizontally();
            ComposerKt.sourceInformationMarkerStart($composer, -483455358, "CC(Column)P(2,3,1)85@4251L61,86@4317L133:Column.kt#2w3rfo");
            MeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(Arrangement.INSTANCE.getTop(), centerHorizontally, $composer, ((390 >> 3) & 14) | ((390 >> 3) & 112));
            ComposerKt.sourceInformationMarkerStart($composer, -1323940314, "CC(Layout)P(!1,2)78@3182L23,81@3333L411:Layout.kt#80mrfh");
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash($composer, 0);
            CompositionLocalMap currentCompositionLocalMap = $composer.getCurrentCompositionLocalMap();
            Modifier materializeModifier = ComposedModifierKt.materializeModifier($composer, m673padding3ABfNKs);
            Function0 constructor = ComposeUiNode.INSTANCE.getConstructor();
            int i = ((((390 << 3) & 112) << 6) & 896) | 6;
            ComposerKt.sourceInformationMarkerStart($composer, -692256719, "CC(ReusableComposeNode)P(1,2)376@14062L9:Composables.kt#9igjgp");
            if (!($composer.getApplier() instanceof Applier)) {
                ComposablesKt.invalidApplier();
            }
            $composer.startReusableNode();
            if ($composer.getInserting()) {
                $composer.createNode(constructor);
            } else {
                $composer.useNode();
            }
            Composer m3655constructorimpl = Updater.m3655constructorimpl($composer);
            Updater.m3662setimpl(m3655constructorimpl, columnMeasurePolicy, ComposeUiNode.INSTANCE.getSetMeasurePolicy());
            Updater.m3662setimpl(m3655constructorimpl, currentCompositionLocalMap, ComposeUiNode.INSTANCE.getSetResolvedCompositionLocals());
            Function2 setCompositeKeyHash = ComposeUiNode.INSTANCE.getSetCompositeKeyHash();
            if (m3655constructorimpl.getInserting() || !Intrinsics.areEqual(m3655constructorimpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                m3655constructorimpl.updateRememberedValue(Integer.valueOf(currentCompositeKeyHash));
                m3655constructorimpl.apply(Integer.valueOf(currentCompositeKeyHash), setCompositeKeyHash);
            }
            Updater.m3662setimpl(m3655constructorimpl, materializeModifier, ComposeUiNode.INSTANCE.getSetModifier());
            int i2 = (i >> 6) & 14;
            ComposerKt.sourceInformationMarkerStart($composer, -384862393, "C87@4365L9:Column.kt#2w3rfo");
            ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
            int i3 = ((390 >> 6) & 112) | 6;
            ComposerKt.sourceInformationMarkerStart($composer, -1421061594, "C267@11269L31,268@11414L11,268@11325L109:PdfTools.kt#ioq88a");
            TextKt.m2696Text4IGK_g("Words", (Modifier) null, 0L, TextUnitKt.getSp(11), (FontStyle) null, (FontWeight) null, (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer, 3078, 0, 131062);
            TextKt.m2696Text4IGK_g(String.valueOf($wordCount), (Modifier) null, MaterialTheme.INSTANCE.getColorScheme($composer, MaterialTheme.$stable).getPrimary(), TextUnitKt.getSp(24), (FontStyle) null, FontWeight.INSTANCE.getBold(), (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer, 199680, 0, 131026);
            ComposerKt.sourceInformationMarkerEnd($composer);
            ComposerKt.sourceInformationMarkerEnd($composer);
            $composer.endNode();
            ComposerKt.sourceInformationMarkerEnd($composer);
            ComposerKt.sourceInformationMarkerEnd($composer);
            ComposerKt.sourceInformationMarkerEnd($composer);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static final Unit WordAndCharacterCounterScreen$lambda$58$lambda$57$lambda$45$lambda$44(int $charCount, ColumnScope Card, Composer $composer, int $changed) {
        Intrinsics.checkNotNullParameter(Card, "$this$Card");
        ComposerKt.sourceInformation($composer, "C275@11705L315:PdfTools.kt#ioq88a");
        if (($changed & 17) == 16 && $composer.getSkipping()) {
            $composer.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(1307452441, $changed, -1, "com.example.ui.tools.pdf.WordAndCharacterCounterScreen.<anonymous>.<anonymous>.<anonymous>.<anonymous> (PdfTools.kt:275)");
            }
            Modifier m673padding3ABfNKs = PaddingKt.m673padding3ABfNKs(Modifier.INSTANCE, Dp.m6625constructorimpl(14));
            Alignment.Horizontal centerHorizontally = Alignment.INSTANCE.getCenterHorizontally();
            ComposerKt.sourceInformationMarkerStart($composer, -483455358, "CC(Column)P(2,3,1)85@4251L61,86@4317L133:Column.kt#2w3rfo");
            MeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(Arrangement.INSTANCE.getTop(), centerHorizontally, $composer, ((390 >> 3) & 14) | ((390 >> 3) & 112));
            ComposerKt.sourceInformationMarkerStart($composer, -1323940314, "CC(Layout)P(!1,2)78@3182L23,81@3333L411:Layout.kt#80mrfh");
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash($composer, 0);
            CompositionLocalMap currentCompositionLocalMap = $composer.getCurrentCompositionLocalMap();
            Modifier materializeModifier = ComposedModifierKt.materializeModifier($composer, m673padding3ABfNKs);
            Function0 constructor = ComposeUiNode.INSTANCE.getConstructor();
            int i = ((((390 << 3) & 112) << 6) & 896) | 6;
            ComposerKt.sourceInformationMarkerStart($composer, -692256719, "CC(ReusableComposeNode)P(1,2)376@14062L9:Composables.kt#9igjgp");
            if (!($composer.getApplier() instanceof Applier)) {
                ComposablesKt.invalidApplier();
            }
            $composer.startReusableNode();
            if ($composer.getInserting()) {
                $composer.createNode(constructor);
            } else {
                $composer.useNode();
            }
            Composer m3655constructorimpl = Updater.m3655constructorimpl($composer);
            Updater.m3662setimpl(m3655constructorimpl, columnMeasurePolicy, ComposeUiNode.INSTANCE.getSetMeasurePolicy());
            Updater.m3662setimpl(m3655constructorimpl, currentCompositionLocalMap, ComposeUiNode.INSTANCE.getSetResolvedCompositionLocals());
            Function2 setCompositeKeyHash = ComposeUiNode.INSTANCE.getSetCompositeKeyHash();
            if (m3655constructorimpl.getInserting() || !Intrinsics.areEqual(m3655constructorimpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                m3655constructorimpl.updateRememberedValue(Integer.valueOf(currentCompositeKeyHash));
                m3655constructorimpl.apply(Integer.valueOf(currentCompositeKeyHash), setCompositeKeyHash);
            }
            Updater.m3662setimpl(m3655constructorimpl, materializeModifier, ComposeUiNode.INSTANCE.getSetModifier());
            int i2 = (i >> 6) & 14;
            ComposerKt.sourceInformationMarkerStart($composer, -384862393, "C87@4365L9:Column.kt#2w3rfo");
            ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
            int i3 = ((390 >> 6) & 112) | 6;
            ComposerKt.sourceInformationMarkerStart($composer, 2086362920, "C276@11826L36,277@11976L11,277@11887L111:PdfTools.kt#ioq88a");
            TextKt.m2696Text4IGK_g("Characters", (Modifier) null, 0L, TextUnitKt.getSp(11), (FontStyle) null, (FontWeight) null, (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer, 3078, 0, 131062);
            TextKt.m2696Text4IGK_g(String.valueOf($charCount), (Modifier) null, MaterialTheme.INSTANCE.getColorScheme($composer, MaterialTheme.$stable).getSecondary(), TextUnitKt.getSp(24), (FontStyle) null, FontWeight.INSTANCE.getBold(), (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer, 199680, 0, 131026);
            ComposerKt.sourceInformationMarkerEnd($composer);
            ComposerKt.sourceInformationMarkerEnd($composer);
            $composer.endNode();
            ComposerKt.sourceInformationMarkerEnd($composer);
            ComposerKt.sourceInformationMarkerEnd($composer);
            ComposerKt.sourceInformationMarkerEnd($composer);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static final Unit WordAndCharacterCounterScreen$lambda$58$lambda$57$lambda$47$lambda$46(MutableState $textInput$delegate, String it) {
        Intrinsics.checkNotNullParameter(it, "it");
        $textInput$delegate.setValue(it);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0216  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0222  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x038d  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0399  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x03d2  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0501  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x050d  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0546  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0675  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0681  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x06ba  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x07f9  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0805  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x083c  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x091d  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x0852 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:83:0x080b  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x06d0 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:86:0x0687  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x055c A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0513  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x03e8 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:92:0x039f  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x0228  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final kotlin.Unit WordAndCharacterCounterScreen$lambda$58$lambda$57$lambda$54(int r105, int r106, int r107, int r108, int r109, androidx.compose.foundation.layout.ColumnScope r110, androidx.compose.runtime.Composer r111, int r112) {
        /*
            Method dump skipped, instructions count: 2339
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.example.ui.tools.pdf.PdfToolsKt.WordAndCharacterCounterScreen$lambda$58$lambda$57$lambda$54(int, int, int, int, int, androidx.compose.foundation.layout.ColumnScope, androidx.compose.runtime.Composer, int):kotlin.Unit");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static final Unit WordAndCharacterCounterScreen$lambda$58$lambda$57$lambda$56(int $charCount, ColumnScope Card, Composer $composer, int $changed) {
        Function0 function0;
        Intrinsics.checkNotNullParameter(Card, "$this$Card");
        ComposerKt.sourceInformation($composer, "C326@14430L554:PdfTools.kt#ioq88a");
        if (($changed & 17) == 16 && $composer.getSkipping()) {
            $composer.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(-1583115403, $changed, -1, "com.example.ui.tools.pdf.WordAndCharacterCounterScreen.<anonymous>.<anonymous>.<anonymous> (PdfTools.kt:326)");
            }
            Modifier m673padding3ABfNKs = PaddingKt.m673padding3ABfNKs(Modifier.INSTANCE, Dp.m6625constructorimpl(16));
            Arrangement.Vertical m553spacedBy0680j_4 = Arrangement.INSTANCE.m553spacedBy0680j_4(Dp.m6625constructorimpl(8));
            ComposerKt.sourceInformationMarkerStart($composer, -483455358, "CC(Column)P(2,3,1)85@4251L61,86@4317L133:Column.kt#2w3rfo");
            MeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(m553spacedBy0680j_4, Alignment.INSTANCE.getStart(), $composer, ((54 >> 3) & 14) | ((54 >> 3) & 112));
            ComposerKt.sourceInformationMarkerStart($composer, -1323940314, "CC(Layout)P(!1,2)78@3182L23,81@3333L411:Layout.kt#80mrfh");
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash($composer, 0);
            CompositionLocalMap currentCompositionLocalMap = $composer.getCurrentCompositionLocalMap();
            Modifier materializeModifier = ComposedModifierKt.materializeModifier($composer, m673padding3ABfNKs);
            Function0 constructor = ComposeUiNode.INSTANCE.getConstructor();
            int i = ((((54 << 3) & 112) << 6) & 896) | 6;
            ComposerKt.sourceInformationMarkerStart($composer, -692256719, "CC(ReusableComposeNode)P(1,2)376@14062L9:Composables.kt#9igjgp");
            if (!($composer.getApplier() instanceof Applier)) {
                ComposablesKt.invalidApplier();
            }
            $composer.startReusableNode();
            if ($composer.getInserting()) {
                function0 = constructor;
                $composer.createNode(function0);
            } else {
                function0 = constructor;
                $composer.useNode();
            }
            Composer m3655constructorimpl = Updater.m3655constructorimpl($composer);
            Updater.m3662setimpl(m3655constructorimpl, columnMeasurePolicy, ComposeUiNode.INSTANCE.getSetMeasurePolicy());
            Updater.m3662setimpl(m3655constructorimpl, currentCompositionLocalMap, ComposeUiNode.INSTANCE.getSetResolvedCompositionLocals());
            Function2 setCompositeKeyHash = ComposeUiNode.INSTANCE.getSetCompositeKeyHash();
            if (m3655constructorimpl.getInserting() || !Intrinsics.areEqual(m3655constructorimpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                m3655constructorimpl.updateRememberedValue(Integer.valueOf(currentCompositeKeyHash));
                m3655constructorimpl.apply(Integer.valueOf(currentCompositeKeyHash), setCompositeKeyHash);
            }
            Updater.m3662setimpl(m3655constructorimpl, materializeModifier, ComposeUiNode.INSTANCE.getSetModifier());
            int i2 = (i >> 6) & 14;
            ComposerKt.sourceInformationMarkerStart($composer, -384862393, "C87@4365L9:Column.kt#2w3rfo");
            ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
            int i3 = ((54 >> 6) & 112) | 6;
            ComposerKt.sourceInformationMarkerStart($composer, -494534029, "C327@14545L57,328@14623L93,329@14737L105,330@14863L103:PdfTools.kt#ioq88a");
            TextKt.m2696Text4IGK_g("Social & SEO Limits", (Modifier) null, 0L, 0L, (FontStyle) null, FontWeight.INSTANCE.getBold(), (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer, 196614, 0, 131038);
            TextKt.m2696Text4IGK_g("• Twitter / X Post: " + $charCount + " / 280 " + ($charCount > 280 ? "❌ Exceeded" : "✅ OK"), (Modifier) null, 0L, 0L, (FontStyle) null, (FontWeight) null, (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer, 0, 0, 131070);
            TextKt.m2696Text4IGK_g("• SMS Text Message: " + $charCount + " / 160 " + ($charCount > 160 ? "❌ (2+ segments)" : "✅ 1 segment"), (Modifier) null, 0L, 0L, (FontStyle) null, (FontWeight) null, (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer, 0, 0, 131070);
            TextKt.m2696Text4IGK_g("• Google SEO Meta Title: " + $charCount + " / 60 " + ($charCount > 60 ? "⚠️ Truncated" : "✅ Optimal"), (Modifier) null, 0L, 0L, (FontStyle) null, (FontWeight) null, (FontFamily) null, 0L, (TextDecoration) null, (TextAlign) null, 0L, 0, false, 0, 0, (Function1<? super TextLayoutResult, Unit>) null, (TextStyle) null, $composer, 0, 0, 131070);
            ComposerKt.sourceInformationMarkerEnd($composer);
            ComposerKt.sourceInformationMarkerEnd($composer);
            $composer.endNode();
            ComposerKt.sourceInformationMarkerEnd($composer);
            ComposerKt.sourceInformationMarkerEnd($composer);
            ComposerKt.sourceInformationMarkerEnd($composer);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        return Unit.INSTANCE;
    }

    public static final void CaseConverterScreen(final Function0<Unit> onBack, Composer $composer, final int $changed) {
        Object obj;
        char c;
        int i;
        Object obj2;
        Object obj3;
        int i2;
        String lowerCase;
        Pair[] pairArr;
        Iterable iterable;
        Intrinsics.checkNotNullParameter(onBack, "onBack");
        Composer $composer2 = $composer.startRestartGroup(-1572311851);
        ComposerKt.sourceInformation($composer2, "C(CaseConverterScreen)344@15253L12,344@15241L24,345@15297L7,347@15327L57,352@15571L46,361@16180L534,375@16721L1838,360@16153L2406:PdfTools.kt#ioq88a");
        int $dirty = $changed;
        if (($changed & 6) == 0) {
            $dirty |= $composer2.changedInstance(onBack) ? 4 : 2;
        }
        if (($dirty & 3) == 2 && $composer2.getSkipping()) {
            $composer2.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(-1572311851, $dirty, -1, "com.example.ui.tools.pdf.CaseConverterScreen (PdfTools.kt:343)");
            }
            ComposerKt.sourceInformationMarkerStart($composer2, 836803041, "CC(remember):PdfTools.kt#9igjgp");
            boolean z = ($dirty & 14) == 4;
            Object rememberedValue = $composer2.rememberedValue();
            if (z || rememberedValue == Composer.INSTANCE.getEmpty()) {
                obj = new Function0() { // from class: com.example.ui.tools.pdf.PdfToolsKt$$ExternalSyntheticLambda5
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return PdfToolsKt.CaseConverterScreen$lambda$61$lambda$60(Function0.this);
                    }
                };
                $composer2.updateRememberedValue(obj);
            } else {
                obj = rememberedValue;
            }
            ComposerKt.sourceInformationMarkerEnd($composer2);
            BackHandlerKt.BackHandler(false, (Function0) obj, $composer2, 0, 1);
            ProvidableCompositionLocal<Context> localContext = AndroidCompositionLocals_androidKt.getLocalContext();
            ComposerKt.sourceInformationMarkerStart($composer2, 2023513938, "CC:CompositionLocal.kt#9igjgp");
            Object consume = $composer2.consume(localContext);
            ComposerKt.sourceInformationMarkerEnd($composer2);
            final Context context = (Context) consume;
            ComposerKt.sourceInformationMarkerStart($composer2, 836805454, "CC(remember):PdfTools.kt#9igjgp");
            Object rememberedValue2 = $composer2.rememberedValue();
            if (rememberedValue2 == Composer.INSTANCE.getEmpty()) {
                c = 4;
                i = 0;
                obj2 = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default("hello world from omnitools", null, 2, null);
                $composer2.updateRememberedValue(obj2);
            } else {
                c = 4;
                i = 0;
                obj2 = rememberedValue2;
            }
            final MutableState inputText$delegate = (MutableState) obj2;
            ComposerKt.sourceInformationMarkerEnd($composer2);
            Pair[] pairArr2 = new Pair[8];
            String upperCase = CaseConverterScreen$lambda$63(inputText$delegate).toUpperCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(upperCase, "toUpperCase(...)");
            pairArr2[i] = TuplesKt.to("UPPERCASE", upperCase);
            String lowerCase2 = CaseConverterScreen$lambda$63(inputText$delegate).toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(lowerCase2, "toLowerCase(...)");
            pairArr2[1] = TuplesKt.to("lowercase", lowerCase2);
            String CaseConverterScreen$lambda$63 = CaseConverterScreen$lambda$63(inputText$delegate);
            String[] strArr = new String[1];
            strArr[i] = " ";
            List split$default = StringsKt.split$default((CharSequence) CaseConverterScreen$lambda$63, strArr, false, 0, 6, (Object) null);
            ComposerKt.sourceInformationMarkerStart($composer2, 836813251, "CC(remember):PdfTools.kt#9igjgp");
            Object rememberedValue3 = $composer2.rememberedValue();
            if (rememberedValue3 == Composer.INSTANCE.getEmpty()) {
                obj3 = new Function1() { // from class: com.example.ui.tools.pdf.PdfToolsKt$$ExternalSyntheticLambda6
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj4) {
                        return PdfToolsKt.CaseConverterScreen$lambda$67$lambda$66((String) obj4);
                    }
                };
                $composer2.updateRememberedValue(obj3);
            } else {
                obj3 = rememberedValue3;
            }
            ComposerKt.sourceInformationMarkerEnd($composer2);
            pairArr2[2] = TuplesKt.to("Title Case", CollectionsKt.joinToString$default(split$default, r18, null, null, 0, null, (Function1) obj3, 30, null));
            String lowerCase3 = CaseConverterScreen$lambda$63(inputText$delegate).toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(lowerCase3, "toLowerCase(...)");
            if ((lowerCase3.length() > 0 ? 1 : i) != 0) {
                StringBuilder sb = new StringBuilder();
                String valueOf = String.valueOf(lowerCase3.charAt(i));
                Intrinsics.checkNotNull(valueOf, "null cannot be cast to non-null type java.lang.String");
                String upperCase2 = valueOf.toUpperCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(upperCase2, "toUpperCase(...)");
                StringBuilder append = sb.append((Object) upperCase2);
                String substring = lowerCase3.substring(1);
                Intrinsics.checkNotNullExpressionValue(substring, "substring(...)");
                lowerCase3 = append.append(substring).toString();
            }
            pairArr2[3] = TuplesKt.to("Sentence case", lowerCase3);
            Iterable split$default2 = StringsKt.split$default((CharSequence) CaseConverterScreen$lambda$63(inputText$delegate), new String[]{" "}, false, 0, 6, (Object) null);
            int i3 = 0;
            Collection arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(split$default2, 10));
            Iterable iterable2 = split$default2;
            int i4 = 0;
            for (Object obj4 : iterable2) {
                int i5 = i4 + 1;
                if (i4 < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                Iterable iterable3 = split$default2;
                String str = (String) obj4;
                if (i4 == 0) {
                    i2 = i3;
                    lowerCase = str.toLowerCase(Locale.ROOT);
                    Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
                    pairArr = pairArr2;
                    iterable = iterable2;
                } else {
                    i2 = i3;
                    lowerCase = str.toLowerCase(Locale.ROOT);
                    Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
                    if (lowerCase.length() > 0) {
                        StringBuilder sb2 = new StringBuilder();
                        pairArr = pairArr2;
                        String valueOf2 = String.valueOf(lowerCase.charAt(0));
                        Intrinsics.checkNotNull(valueOf2, "null cannot be cast to non-null type java.lang.String");
                        iterable = iterable2;
                        String upperCase3 = valueOf2.toUpperCase(Locale.ROOT);
                        Intrinsics.checkNotNullExpressionValue(upperCase3, "toUpperCase(...)");
                        StringBuilder append2 = sb2.append((Object) upperCase3);
                        String substring2 = lowerCase.substring(1);
                        Intrinsics.checkNotNullExpressionValue(substring2, "substring(...)");
                        lowerCase = append2.append(substring2).toString();
                    } else {
                        pairArr = pairArr2;
                        iterable = iterable2;
                    }
                }
                arrayList.add(lowerCase);
                i4 = i5;
                split$default2 = iterable3;
                i3 = i2;
                pairArr2 = pairArr;
                iterable2 = iterable;
            }
            Pair[] pairArr3 = pairArr2;
            pairArr3[c] = TuplesKt.to("camelCase", CollectionsKt.joinToString$default((List) arrayList, "", null, null, 0, null, null, 62, null));
            String lowerCase4 = CaseConverterScreen$lambda$63(inputText$delegate).toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(lowerCase4, "toLowerCase(...)");
            pairArr3[5] = TuplesKt.to("snake_case", StringsKt.replace$default(lowerCase4, " ", "_", false, 4, (Object) null));
            String lowerCase5 = CaseConverterScreen$lambda$63(inputText$delegate).toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(lowerCase5, "toLowerCase(...)");
            pairArr3[6] = TuplesKt.to("kebab-case", StringsKt.replace$default(lowerCase5, " ", "-", false, 4, (Object) null));
            CharSequence CaseConverterScreen$lambda$632 = CaseConverterScreen$lambda$63(inputText$delegate);
            Collection arrayList2 = new ArrayList(CaseConverterScreen$lambda$632.length());
            int i6 = 0;
            int i7 = 0;
            while (i7 < CaseConverterScreen$lambda$632.length()) {
                char charAt = CaseConverterScreen$lambda$632.charAt(i7);
                int i8 = i6 + 1;
                arrayList2.add(Character.valueOf(i6 % 2 == 0 ? Character.toLowerCase(charAt) : Character.toUpperCase(charAt)));
                i7++;
                i6 = i8;
            }
            pairArr3[7] = TuplesKt.to("Alternating cAsE", CollectionsKt.joinToString$default((List) arrayList2, "", null, null, 0, null, null, 62, null));
            final List conversions = CollectionsKt.listOf((Object[]) pairArr3);
            ScaffoldKt.m2411ScaffoldTvnljyQ(null, ComposableLambdaKt.rememberComposableLambda(669096337, true, new Function2() { // from class: com.example.ui.tools.pdf.PdfToolsKt$$ExternalSyntheticLambda7
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj5, Object obj6) {
                    return PdfToolsKt.CaseConverterScreen$lambda$74(Function0.this, (Composer) obj5, ((Integer) obj6).intValue());
                }
            }, $composer2, 54), null, null, null, 0, 0L, 0L, null, ComposableLambdaKt.rememberComposableLambda(527966438, true, new Function3() { // from class: com.example.ui.tools.pdf.PdfToolsKt$$ExternalSyntheticLambda8
                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj5, Object obj6, Object obj7) {
                    return PdfToolsKt.CaseConverterScreen$lambda$84(conversions, inputText$delegate, context, (PaddingValues) obj5, (Composer) obj6, ((Integer) obj7).intValue());
                }
            }, $composer2, 54), $composer2, 805306416, 509);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        ScopeUpdateScope endRestartGroup = $composer2.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.updateScope(new Function2() { // from class: com.example.ui.tools.pdf.PdfToolsKt$$ExternalSyntheticLambda9
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj5, Object obj6) {
                    return PdfToolsKt.CaseConverterScreen$lambda$85(Function0.this, $changed, (Composer) obj5, ((Integer) obj6).intValue());
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static final Unit CaseConverterScreen$lambda$61$lambda$60(Function0 $onBack) {
        $onBack.invoke();
        return Unit.INSTANCE;
    }

    private static final String CaseConverterScreen$lambda$63(MutableState<String> mutableState) {
        return mutableState.getValue();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static final CharSequence CaseConverterScreen$lambda$67$lambda$66(String it) {
        String str;
        Intrinsics.checkNotNullParameter(it, "it");
        if (it.length() > 0) {
            StringBuilder sb = new StringBuilder();
            String valueOf = String.valueOf(it.charAt(0));
            Intrinsics.checkNotNull(valueOf, "null cannot be cast to non-null type java.lang.String");
            String upperCase = valueOf.toUpperCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(upperCase, "toUpperCase(...)");
            StringBuilder append = sb.append((Object) upperCase);
            String substring = it.substring(1);
            Intrinsics.checkNotNullExpressionValue(substring, "substring(...)");
            str = append.append(substring).toString();
        } else {
            str = it;
        }
        return str;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Removed duplicated region for block: B:25:0x01ab  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final kotlin.Unit CaseConverterScreen$lambda$74(final kotlin.jvm.functions.Function0 r41, androidx.compose.runtime.Composer r42, int r43) {
        /*
            Method dump skipped, instructions count: 433
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.example.ui.tools.pdf.PdfToolsKt.CaseConverterScreen$lambda$74(kotlin.jvm.functions.Function0, androidx.compose.runtime.Composer, int):kotlin.Unit");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static final Unit CaseConverterScreen$lambda$74$lambda$73$lambda$72(Function0 $onBack, Composer $composer, int $changed) {
        ComposerKt.sourceInformation($composer, "C366@16349L155:PdfTools.kt#ioq88a");
        if (($changed & 3) == 2 && $composer.getSkipping()) {
            $composer.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(-1509597151, $changed, -1, "com.example.ui.tools.pdf.CaseConverterScreen.<anonymous>.<anonymous>.<anonymous> (PdfTools.kt:366)");
            }
            IconButtonKt.IconButton($onBack, null, false, null, null, ComposableSingletons$PdfToolsKt.INSTANCE.m7149getLambda$1176345858$app(), $composer, ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_CANT_WRITE_PROFILE_VERIFICATION_RESULT_CACHE_FILE, 30);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Removed duplicated region for block: B:32:0x01b0  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0225 A[LOOP:0: B:34:0x021f->B:36:0x0225, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:40:0x02ea  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x01c2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final kotlin.Unit CaseConverterScreen$lambda$84(java.util.List r57, final androidx.compose.runtime.MutableState r58, final android.content.Context r59, androidx.compose.foundation.layout.PaddingValues r60, androidx.compose.runtime.Composer r61, int r62) {
        /*
            Method dump skipped, instructions count: 752
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.example.ui.tools.pdf.PdfToolsKt.CaseConverterScreen$lambda$84(java.util.List, androidx.compose.runtime.MutableState, android.content.Context, androidx.compose.foundation.layout.PaddingValues, androidx.compose.runtime.Composer, int):kotlin.Unit");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static final Unit CaseConverterScreen$lambda$84$lambda$83$lambda$76$lambda$75(MutableState $inputText$delegate, String it) {
        Intrinsics.checkNotNullParameter(it, "it");
        $inputText$delegate.setValue(it);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Removed duplicated region for block: B:25:0x01d2  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x01de  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0210  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x032e  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0383  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0226  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x01e2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final kotlin.Unit CaseConverterScreen$lambda$84$lambda$83$lambda$82$lambda$81(final android.content.Context r72, final java.lang.String r73, final java.lang.String r74, androidx.compose.foundation.layout.ColumnScope r75, androidx.compose.runtime.Composer r76, int r77) {
        /*
            Method dump skipped, instructions count: 905
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.example.ui.tools.pdf.PdfToolsKt.CaseConverterScreen$lambda$84$lambda$83$lambda$82$lambda$81(android.content.Context, java.lang.String, java.lang.String, androidx.compose.foundation.layout.ColumnScope, androidx.compose.runtime.Composer, int):kotlin.Unit");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static final Unit CaseConverterScreen$lambda$84$lambda$83$lambda$82$lambda$81$lambda$80$lambda$79$lambda$78(Context $context, String $converted, String $type) {
        ImageExportUtils.INSTANCE.copyToClipboard($context, $converted, $type);
        return Unit.INSTANCE;
    }
}
