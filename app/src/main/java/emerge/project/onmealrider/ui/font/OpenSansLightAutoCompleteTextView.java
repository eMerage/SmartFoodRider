package emerge.project.onmealrider.ui.font;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;

/**
 * Created by attract on 3/12/15.
 */

public class OpenSansLightAutoCompleteTextView extends AutoCompleteTextView {
    public OpenSansLightAutoCompleteTextView(Context context) {
        super(context);
        init();
    }

    public OpenSansLightAutoCompleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public OpenSansLightAutoCompleteTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "Font/OpenSans-Light.ttf");
        setTypeface(tf);
    }
}
