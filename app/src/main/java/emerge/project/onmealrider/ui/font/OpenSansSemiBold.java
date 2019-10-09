package emerge.project.onmealrider.ui.font;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by attract on 3/12/15.
 */

public class OpenSansSemiBold extends TextView {
    public OpenSansSemiBold(Context context) {
        super(context);
        init();
    }

    public OpenSansSemiBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public OpenSansSemiBold(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "Font/OpenSans-SemiBold.ttf");
        setTypeface(tf);
    }
}
