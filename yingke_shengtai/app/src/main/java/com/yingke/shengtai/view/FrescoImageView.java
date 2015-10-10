package com.yingke.shengtai.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

/**
 * Fresco Gotchas 
 * 
 * link: http://frescolib.org/docs/gotchas.html#_ 
 * 
 * Don't downcast
 * It is tempting to downcast objects returns by Fresco classes into actual objects that appear to give you greater control. 
 * At best, this will result in fragile code that gets broken next release; at worst, it will lead to very subtle bugs.
 *
 * Don't use getTopLevelDrawable
 * DraweeHierarchy.getTopLevelDrawable() should only be used by DraweeViews. Client code should almost never interact with it.
 * The sole exception is custom views. Even there, the top-level drawable should never be downcast. We may change the actual type of the drawable in future releases.
 *
 * Don't re-use DraweeHierarchies
 * Never call DraweeView.setHierarchy with the same argument on two different views. Hierarchies are made up of Drawables, 
 * and Drawables on Android cannot be shared among multiple views.
 *
 * Don't use Drawables in more than one DraweeHierarchy
 * This is for the same reason as the above. Drawables cannot be shared in multiple views.
 * You are completely free, of course, to use the same resourceID in multiple hierarchies and views. Android will create a separate instance of each Drawable for each view.
 * 
 * Don't set images directly on a DraweeView
 * Currently DraweeView is a subclass of Android's ImageView. This has various methods to set an image (such as setImageBitmap, setImageDrawable)
 * If you set an image directly, you will completely lose your DraweeHierarchy, and will not get any results from the image pipeline.
 *
 * Don't use ImageView attributes or methods with DraweeView
 * Any XML attribute or method of ImageView not found in View will not work on a DraweeView. Typical cases are scaleType, src, etc. Don't use those. 
 * DraweeView has its own counterparts as explained in the other sections of this documentation. 
 * Any ImageView attrribute or method will be removed in the upcoming release, so please don't use those.
 * 
 * */

public class FrescoImageView extends SimpleDraweeView {
	
	public FrescoImageView(Context context) {
		this(context, null);
	}
	
	public FrescoImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
    /**
     * Controls how the image should be resized or moved to match the size
     * of this FrescoImageView.
     *
     * @attr ref actualImageScaleType
     */	
	public void setFrescoImageScaleType(ScalingUtils.ScaleType actualImageScaleType) {
		GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(getResources());
		GenericDraweeHierarchy hierarchy = builder.setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER).build();
		this.setHierarchy(hierarchy);
	}
	
	/**
	 * animated image to start playing automatically when it comes on-screen, and stop when it goes off
	 * 
	 * @param imgUrl The image online url
	 */
	public void displayAnimatedImage(String imgUrl) {
		if(TextUtils.isEmpty(imgUrl)) return;
		ImageRequest request = ImageRequestBuilder
			    .newBuilderWithSource(Uri.parse(imgUrl))
			    .setProgressiveRenderingEnabled(true)
			    .build();
		DraweeController controller = Fresco.newDraweeControllerBuilder()
				.setImageRequest(request)
				.setAutoPlayAnimations(true)
				.setOldController(this.getController())
				.build();
		this.setController(controller);
	}
	
	public void displayImage(final String uri) {
		if(TextUtils.isEmpty(uri)) {
			return;
		}
//		String tag = (String) this.getTag();
//		if ((tag == null || !TextUtils.equals(uri, tag))) {
			this.setImageURI(Uri.parse(uri));
//		}
//		this.setTag(uri);
	}
	
	public void setProgressListener(Drawable progressBarImage) {
		GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(getResources());
		GenericDraweeHierarchy hierarchy = builder
			.setProgressBarImage(progressBarImage)
		    .build();
		this.setHierarchy(hierarchy);
	}
	
}
