package com.example.ads;

import android.content.Context;
import android.util.Log;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.ui.Modifier;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: AdManager.kt */
@Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\u001a\u0017\u0010\u0000\u001a\u00020\u00012\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u0007¢\u0006\u0002\u0010\u0004¨\u0006\u0005²\u0006\n\u0010\u0006\u001a\u00020\u0007X\u008a\u008e\u0002"}, d2 = {"TopBannerAd", "", "modifier", "Landroidx/compose/ui/Modifier;", "(Landroidx/compose/ui/Modifier;Landroidx/compose/runtime/Composer;II)V", "app", "isAdFailed", ""}, k = 2, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class AdManagerKt {
    /* JADX INFO: Access modifiers changed from: package-private */
    public static final Unit TopBannerAd$lambda$7(Modifier modifier, int i, int i2, Composer composer, int i3) {
        TopBannerAd(modifier, composer, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
        return Unit.INSTANCE;
    }

    /* JADX WARN: Removed duplicated region for block: B:38:0x01ed  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x01fb  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final void TopBannerAd(androidx.compose.ui.Modifier r38, androidx.compose.runtime.Composer r39, final int r40, final int r41) {
        /*
            Method dump skipped, instructions count: 592
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.example.ads.AdManagerKt.TopBannerAd(androidx.compose.ui.Modifier, androidx.compose.runtime.Composer, int, int):void");
    }

    private static final boolean TopBannerAd$lambda$1(MutableState<Boolean> mutableState) {
        return mutableState.getValue().booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void TopBannerAd$lambda$2(MutableState<Boolean> mutableState, boolean z) {
        mutableState.setValue(Boolean.valueOf(z));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static final AdView TopBannerAd$lambda$6$lambda$5$lambda$4(final MutableState $isAdFailed$delegate, Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        AdView adView = new AdView(ctx);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(AdManager.INSTANCE.getBANNER_ID());
        adView.setAdListener(new AdListener() { // from class: com.example.ads.AdManagerKt$TopBannerAd$1$1$1$1$1
            @Override // com.google.android.gms.ads.AdListener
            public void onAdFailedToLoad(LoadAdError error) {
                Intrinsics.checkNotNullParameter(error, "error");
                Log.w("TopBannerAd", "Banner failed: " + error.getMessage());
                AdManagerKt.TopBannerAd$lambda$2($isAdFailed$delegate, true);
            }

            @Override // com.google.android.gms.ads.AdListener
            public void onAdLoaded() {
                AdManagerKt.TopBannerAd$lambda$2($isAdFailed$delegate, false);
            }
        });
        adView.loadAd(new AdRequest.Builder().build());
        return adView;
    }
}
