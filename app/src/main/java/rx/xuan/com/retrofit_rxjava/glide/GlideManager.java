package rx.xuan.com.retrofit_rxjava.glide;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.animation.ViewPropertyAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;

import rx.xuan.com.retrofit_rxjava.R;

/**
 * @author xiansenxuan
 */
public class GlideManager {
	
	/*
	  
	.fitCenter()//图片显示全部 但是imageview可能填充不全
	.centerCrop()//填充全部imageview 但是图片显示不全 
	.thumbnail( 0.3f )//缩略图
	  
	自定义动画
	 ViewPropertyAnimation.Animator animationObject = new ViewPropertyAnimation.Animator() {  
	    @Override
	    public void animate(View view) {
	        // if it's a custom view class, cast it here
	        // then find subviews and do the animations
	        // here, we just use the entire view for the fade animation
	        view.setAlpha( 0f );

	        ObjectAnimator fadeAnim = ObjectAnimator.ofFloat( view, "alpha", 0f, 1f );
	        fadeAnim.setDuration( 2500 );
	        fadeAnim.start();
	    }
	};

	


	*/
	
	/**
	 * 渐显动画时间 ms
	 */
	private static final int duration=300;
	
	/**
	 * 加载网络图片 自定义图片大小
	 * @param context
	 * @param url
	 * @param iv
	 */
	public static void setCustomImage(Activity context, String url, ImageView iv) {
		CustomImageSizeGlideModule module=new CustomImageSizeGlideModule();
		module.registerComponents(context, Glide.get(context));
		
		CustomImageSizeModel customImageRequest = new CustomImageSizeModelFutureStudio( url );
		customImageRequest.requestCustomSizeUrl(200, 200);
		
		Glide.with(context)
		.load(customImageRequest)
		.placeholder(R.drawable.before)//加载之前的 默认占位图
		.error(R.drawable.broken)//图片无法加载时的 默认占位图
		.crossFade(duration)//淡入淡出动画 默认300ms
		.into(iv);
	}
	
	/**
	 * 使用bitmap回调 指定大小加载
	 * @param context
	 * @param url
	 * @param iv
	 */
	public static void getBitmap(Activity context, String url, final ImageView iv,int width,int height){
	    Glide
	        .with( context ) // could be an issue!
	        .load( url )
	        .asBitmap()
	        .into( new SimpleTarget<Bitmap>(width,height) {
	    		    @Override
	    		    public void onResourceReady(Bitmap bitmap, @SuppressWarnings("rawtypes") GlideAnimation glideAnimation) {
	    		        // do something with the bitmap
	    		        // for demonstration purposes, let's just set it to an ImageView
	    		    	iv.setImageBitmap( bitmap );
	    		    }
	    		});
	}
	
	/**
	 * 加载网络图片 圆形
	 * @param context
	 * @param url
	 * @param iv
	 */
	public static void setRoundImage(final Activity context, String url, final ImageView iv) {
		ViewPropertyAnimation.Animator animationObject = new ViewPropertyAnimation.Animator() {
		    @Override
		    public void animate(View view) {
		        // if it's a custom view class, cast it here
		        // then find subviews and do the animations
		        // here, we just use the entire view for the fade animation
		        view.setAlpha( 0f );

		        ObjectAnimator fadeAnim = ObjectAnimator.ofFloat( view, "alpha", 0f, 1f );
		        fadeAnim.setDuration( duration );
		        fadeAnim.start();
		    }
		};
		
		Glide.with(context).load(url).asBitmap().centerCrop()
		.placeholder(R.drawable.before)
		.error(R.drawable.broken)
		.animate(animationObject)
		.into(new BitmapImageViewTarget(iv) {
			@Override
			protected void setResource(Bitmap resource) {
				RoundedBitmapDrawable circularBitmapDrawable =
						RoundedBitmapDrawableFactory.create(context.getResources(), resource);
				Bitmap dst;
				//将长方形图片裁剪成正方形图片
				if (resource.getWidth() >= resource.getHeight()){
				    dst = Bitmap.createBitmap(resource, resource.getWidth()/2 - resource.getHeight()/2, 0, resource.getHeight(), resource.getHeight()
				    );
				}else{
				    dst = Bitmap.createBitmap(resource, 0, resource.getHeight()/2 - resource.getWidth()/2, resource.getWidth(), resource.getWidth()
				    );
				}
				circularBitmapDrawable.setCornerRadius(dst.getWidth() / 2); //设置圆角半径为正方形边长的一半
				circularBitmapDrawable.setAntiAlias(true);
				
				iv.setImageDrawable(circularBitmapDrawable);
			}
		});
	}
	
	/**
	 * 加载本地图片 圆形
	 * @param context
	 * @param resId
	 * @param iv
	 */
	public static void setRoundImage(final Activity context, int resId, final ImageView iv) {
		ViewPropertyAnimation.Animator animationObject = new ViewPropertyAnimation.Animator() {  
		    @Override
		    public void animate(View view) {
		        // if it's a custom view class, cast it here
		        // then find subviews and do the animations
		        // here, we just use the entire view for the fade animation
		        view.setAlpha( 0f );

		        ObjectAnimator fadeAnim = ObjectAnimator.ofFloat( view, "alpha", 0f, 1f );
		        fadeAnim.setDuration( duration );
		        fadeAnim.start();
		    }
		};
		
		Glide.with(context).load(resId).asBitmap().centerCrop()
		.placeholder(R.drawable.before)
		.error(R.drawable.broken)
		.animate(animationObject)
		.into(new BitmapImageViewTarget(iv) {
			@Override
			protected void setResource(Bitmap resource) {
				RoundedBitmapDrawable circularBitmapDrawable =
						RoundedBitmapDrawableFactory.create(context.getResources(), resource);
				Bitmap dst;
				//将长方形图片裁剪成正方形图片
				if (resource.getWidth() >= resource.getHeight()){
				    dst = Bitmap.createBitmap(resource, resource.getWidth()/2 - resource.getHeight()/2, 0, resource.getHeight(), resource.getHeight()
				    );
				}else{
				    dst = Bitmap.createBitmap(resource, 0, resource.getHeight()/2 - resource.getWidth()/2, resource.getWidth(), resource.getWidth()
				    );
				}
				circularBitmapDrawable.setCornerRadius(dst.getWidth() / 2); //设置圆角半径为正方形边长的一半
				circularBitmapDrawable.setAntiAlias(true);
				
				iv.setImageDrawable(circularBitmapDrawable);
			}
		});
	}
	
	/**
	 * 加载网络图片
	 * @param context
	 * @param url
	 * @param iv
	 */
	public static void setNormalImage(Activity context, String url, ImageView iv) {
		Glide.with(context)
		.load(url)
		.placeholder(R.drawable.before)//加载之前的 默认占位图
		.error(R.drawable.broken)//图片无法加载时的 默认占位图
		.crossFade(duration)//淡入淡出动画 默认300ms
		.into(iv);
	}
	
	/**
	 * 加载网络图片 自定义动画
	 * @param context
	 * @param url
	 * @param iv
	 */
	public static void setNormalImage(Activity context, String url, ImageView iv,int animId) {
		Glide.with(context)
		.load(url)
		.placeholder(R.drawable.before)//加载之前的 默认占位图
		.error(R.drawable.broken)//图片无法加载时的 默认占位图
		.animate(animId)
		.into(iv);
	}
	
	/**
	 * 加载本地图片
	 * @param context
	 * @param resId
	 * @param iv
	 */
	public static void setNormalImage(Activity context, int resId, ImageView iv) {
		Glide.with(context)
		.load(resId)
		.placeholder(R.drawable.before)//加载之前的 默认占位图
		.error(R.drawable.broken)//图片无法加载时的 默认占位图
		.crossFade(duration)//淡入淡出动画 默认300ms
		.into(iv);
	}
	
	/**
	 * 加载网络图片 自定义动画
	 * @param context
	 * @param resId
	 * @param iv
	 */
	public static void setNormalImage(Activity context, int resId, ImageView iv,int animId) {
		Glide.with(context)
		.load(resId)
		.placeholder(R.drawable.before)//加载之前的 默认占位图
		.error(R.drawable.broken)//图片无法加载时的 默认占位图
		.animate(animId)
		.into(iv);
	}
	
	
	
}
