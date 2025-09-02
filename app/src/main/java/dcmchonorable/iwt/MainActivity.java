package dcmchonorable.iwt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.*;
import android.app.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.webkit.*;
import android.animation.*;
import android.view.animation.*;
import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.text.*;
import org.json.*;
import android.widget.LinearLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.airbnb.lottie.*;
import android.content.Intent;
import android.net.Uri;
import java.util.Timer;
import java.util.TimerTask;
import android.app.Activity;
import android.content.SharedPreferences;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ChildEventListener;
import java.util.HashMap;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;

public class MainActivity extends AppCompatActivity {
	
	private Timer _timer = new Timer();
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private String fontName = "";
	private String typeace = "";
	private String text = "";
	private String ID = "";
	
	private LinearLayout linear1;
	private LinearLayout linear2;
	private ImageView imageview1;
	private LinearLayout linear3;
	private LinearLayout linear7;
	private LinearLayout linear6;
	private LinearLayout linear5;
	private TextView textview1;
	private ImageView imageview2;
	private LinearLayout linear4;
	private LottieAnimationView lottie1;
	private TextView textview3;
	
	private Intent i = new Intent();
	private TimerTask time;
	private SharedPreferences keyStore;
	private DatabaseReference ref = _firebase.getReference("ref");
	private ChildEventListener _ref_child_listener;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.main);
		initialize(_savedInstanceState);
		com.google.firebase.FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		linear1 = findViewById(R.id.linear1);
		linear2 = findViewById(R.id.linear2);
		imageview1 = findViewById(R.id.imageview1);
		linear3 = findViewById(R.id.linear3);
		linear7 = findViewById(R.id.linear7);
		linear6 = findViewById(R.id.linear6);
		linear5 = findViewById(R.id.linear5);
		textview1 = findViewById(R.id.textview1);
		imageview2 = findViewById(R.id.imageview2);
		linear4 = findViewById(R.id.linear4);
		lottie1 = findViewById(R.id.lottie1);
		textview3 = findViewById(R.id.textview3);
		keyStore = getSharedPreferences("keyStore", Activity.MODE_PRIVATE);
		
		linear5.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				for(int _repeat10 = 0; _repeat10 < (int)(SketchwareUtil.getDip(getApplicationContext(), (int)(SketchwareUtil.getDisplayHeightPixels(getApplicationContext())))); _repeat10++) {
					
				}
			}
		});
		
		_ref_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				// Vérification renforcée de l'accès premium
				final SharedPreferences keyStore = getSharedPreferences("keyStore", MODE_PRIVATE);
				final String userKey = keyStore.getString("userKey", "");
				final String savedStatus = keyStore.getString("key", "non_actif");
				
				// Vérifier immédiatement si l'utilisateur n'a jamais été activé
				if (TextUtils.isEmpty(userKey) || savedStatus.equals("non_actif")) {
					    _showDeactivatedMessage();
					    return;
				}
				
				// Vérification en temps réel avec Firebase
				FirebaseDatabase database = FirebaseDatabase.getInstance();
				DatabaseReference ref = database.getReference("KEYS");
				
				ref.child(userKey).addListenerForSingleValueEvent(new ValueEventListener() {
					    @Override
					    public void onDataChange(DataSnapshot dataSnapshot) {
						        if (!dataSnapshot.exists()) {
							            // Clé supprimée de Firebase
							            keyStore.edit()
							                .putString("key", "non_actif")
							                .remove("userKey") // Nettoyage supplémentaire
							                .apply();
							            _showDeactivatedMessage();
							            return;
							        }
						
						        String firebaseValue = dataSnapshot.getValue(String.class);
						        if (firebaseValue == null || !firebaseValue.equals(ID != null ? ID : "")) {
							            // Clé existe mais ne correspond pas à l'ID actuel
							            keyStore.edit()
							                .putString("key", "non_actif")
							                .apply();
							            _showDeactivatedMessage();
							        } else {
							            // Tout est valide - accès premium confirmé
							            keyStore.edit()
							                .putString("key", "actif")
							                .apply();
							           // _grantPremiumAccess(); // Méthode pour activer les fonctionnalités premium
							        }
						    }
					
					    @Override
					    public void onCancelled(DatabaseError databaseError) {
						        // En cas d'erreur, on pourrait choisir de:
						        // 1. Bloquer l'accès par sécurité
						        // keyStore.edit().putString("key", "non_actif").apply();
						        // _showDeactivatedMessage();
						        
						        // OU 2. Autoriser temporairement (selon votre politique)
						        SketchwareUtil.showMessage(getApplicationContext(), 
						            "Erreur de vérification. Accès limité.");
						    }
				});
				
			}
			// Méthode pour afficher un message de désactivation
			private void _showDeactivatedMessage() {
				    SketchwareUtil.showMessage(getApplicationContext(), "Votre accès a été désactivé. Veuillez contacter l'administrateur.");
			}
			{
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onChildMoved(DataSnapshot _param1, String _param2) {
				
			}
			
			@Override
			public void onChildRemoved(DataSnapshot _param1) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onCancelled(DatabaseError _param1) {
				final int _errorCode = _param1.getCode();
				final String _errorMessage = _param1.getMessage();
				
			}
		};
		ref.addChildEventListener(_ref_child_listener);
	}
	
	private void initializeLogic() {
		ID = android.os.Build.ID ;
		 if ("actif".equals(keyStore.getString("key", ""))) {
							
		  lottie1.setAnimationFromUrl("file:///android_asset/trophy_animation.json");
			 
		 } else {
			lottie1.setAnimationFromUrl("file:///android_asset/other_animation.json");
			 
		 }
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
			Window w =MainActivity.this.getWindow();
			w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS); w.setStatusBarColor(0xFF0700FF);
		}
		{
			android.graphics.drawable.GradientDrawable SketchUi = new android.graphics.drawable.GradientDrawable();
			int clrs [] = {0xFF0700FF,0xFF7456F6,0xFF4400FF};
			SketchUi= new android.graphics.drawable.GradientDrawable(android.graphics.drawable.GradientDrawable.Orientation.TOP_BOTTOM, clrs);
			linear2.setBackground(SketchUi);
		}
		{
			android.graphics.drawable.GradientDrawable SketchUi = new android.graphics.drawable.GradientDrawable();
			int d = (int) getApplicationContext().getResources().getDisplayMetrics().density;
			SketchUi.setColor(0xFFFFFFFF);
			SketchUi.setCornerRadius(d*26);
			linear3.setElevation(d*2);
			linear3.setBackground(SketchUi);
		}
		{
			android.graphics.drawable.GradientDrawable SketchUi = new android.graphics.drawable.GradientDrawable();
			int d = (int) getApplicationContext().getResources().getDisplayMetrics().density;
			int clrs [] = {0xFF0700FF,0xFF4400FF};
			SketchUi= new android.graphics.drawable.GradientDrawable(android.graphics.drawable.GradientDrawable.Orientation.TOP_BOTTOM, clrs);
			SketchUi.setCornerRadius(d*14);
			android.graphics.drawable.RippleDrawable SketchUi_RD = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{0xFFE0E0E0}), SketchUi, null);
			linear4.setBackground(SketchUi_RD);
			linear4.setClickable(true);
		}
		text = "INFAS WEBSITE TOGETHER";
		// Dans votre activité
		AutoTypeTextView tv = new AutoTypeTextView(MainActivity.this);
		tv.setLayoutParams(new LinearLayout.LayoutParams(
		    LinearLayout.LayoutParams.MATCH_PARENT,
		    LinearLayout.LayoutParams.MATCH_PARENT));
		tv.setTypingSpeed(130); // Réglage de la vitesse
		tv.setTextAutoTyping(text);
		tv.setTextSize(20);
		tv.setBackgroundColor(Color.BLACK);
		linear6.addView(tv);
		/*
AutoTypeTextView1 tv1 = new AutoTypeTextView1(MainActivity.this);
tv1.setLayoutParams(new LinearLayout.LayoutParams(
    LinearLayout.LayoutParams.MATCH_PARENT,
    LinearLayout.LayoutParams.MATCH_PARENT));
tv1.setTypingSpeed(10); // Réglage de la vitesse
tv1.setTextAutoTyping("https://sujetsinfas.com, Se réunir est un début, rester ensemble est un progrès, travailler ensemble est la réussite.");
tv1.setTextSize(18);
tv1.setBackgroundColor(Color.WHITE);
linear7.addView(tv1);*/
		
		time = new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						i.setClass(getApplicationContext(), HomeActivity.class);
						startActivity(i);
						finish();
					}
				});
			}
		};
		_timer.schedule(time, (int)(6000));
	}
	
	public void _ImageGradientLib() {
		/*
* WELCOME TO EASY LIBARY
*
* DO NOT DELETE THIS, IF U DELETE U MUST GIVE A TAG IN PROJECT
*
* Now How to use this small libary, it's very simple
* u need to just use this tiny code
* setColorFilter(your_imageId, "LEFT_RIGHT", "#FF0000", "#FF2500");
*
* After u use that code u will get gradient in image
*/
	}
	public void setColorFilter(ImageView _view, String _orientation, String _start, String _end) {
			if (_orientation.equals("TOP_BOTTOM")) {
					final ImageView _image = _view;
					Bitmap myBitmap = ((BitmapDrawable) _image.getDrawable()).getBitmap();
					Bitmap newBitmap = addGradientTB(myBitmap, _start, _end);
					_image.setImageDrawable(new BitmapDrawable(getResources(), newBitmap));
			} else {
					if (_orientation.equals("BOTTOM_TOP")) {
							final ImageView _image = _view;
							Bitmap myBitmap = ((BitmapDrawable) _image.getDrawable()).getBitmap();
							Bitmap newBitmap = addGradientBT(myBitmap, _start, _end);
							_image.setImageDrawable(new BitmapDrawable(getResources(), newBitmap));
					} else {
							if (_orientation.equals("LEFT_RIGHT")) {
									final ImageView _image = _view;
									Bitmap myBitmap = ((BitmapDrawable) _image.getDrawable()).getBitmap();
									Bitmap newBitmap = addGradientLR(myBitmap, _start, _end);
									_image.setImageDrawable(new BitmapDrawable(getResources(), newBitmap));
							} else {
									if (_orientation.equals("RIGHT_LEFT")) {
											final ImageView _image = _view;
											Bitmap myBitmap = ((BitmapDrawable) _image.getDrawable()).getBitmap();
											Bitmap newBitmap = addGradientRL(myBitmap, _start, _end);
											_image.setImageDrawable(new BitmapDrawable(getResources(), newBitmap));
									} else {
											showMessage("Wrong Orientation seted!");
									}
							}
					}
			}
	}
	
	public Bitmap addGradientTB(Bitmap originalBitmap, String _start, String _end) {
			int width = originalBitmap.getWidth();
			int height = originalBitmap.getHeight();
			
			Bitmap updatedBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
			Canvas canvas = new Canvas(updatedBitmap);
			canvas.drawBitmap(originalBitmap, 0, 0, null);
			Paint paint = new Paint();
			
			LinearGradient shader = new LinearGradient(0, 0, 0, height, Color.parseColor(_start), Color.parseColor(_end), Shader.TileMode.CLAMP);
			paint.setShader(shader);
			paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
			canvas.drawRect(0, 0, width, height, paint);
			
			return updatedBitmap;
	}
	
	public Bitmap addGradientBT(Bitmap originalBitmap, String _start, String _end) {
			int width = originalBitmap.getWidth();
			int height = originalBitmap.getHeight();
			
			Bitmap updatedBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
			Canvas canvas = new Canvas(updatedBitmap);
			canvas.drawBitmap(originalBitmap, 0, 0, null);
			Paint paint = new Paint();
			
			LinearGradient shader = new LinearGradient(0, height, 0, 0, Color.parseColor(_start), Color.parseColor(_end), Shader.TileMode.CLAMP);
			paint.setShader(shader);
			paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
			canvas.drawRect(0, 0, width, height, paint);
			
			return updatedBitmap;
	}
	
	public Bitmap addGradientLR(Bitmap originalBitmap, String _start, String _end) {
			int width = originalBitmap.getWidth();
			int height = originalBitmap.getHeight();
			
			Bitmap updatedBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
			Canvas canvas = new Canvas(updatedBitmap);
			canvas.drawBitmap(originalBitmap, 0, 0, null);
			Paint paint = new Paint();
			
			LinearGradient shader = new LinearGradient(0, 0, width, 0, Color.parseColor(_start), Color.parseColor(_end), Shader.TileMode.CLAMP);
			paint.setShader(shader);
			paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
			canvas.drawRect(0, 0, width, height, paint);
			
			return updatedBitmap;
	}
	
	public Bitmap addGradientRL(Bitmap originalBitmap, String _start, String _end) {
			int width = originalBitmap.getWidth();
			int height = originalBitmap.getHeight();
			
			Bitmap updatedBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
			Canvas canvas = new Canvas(updatedBitmap);
			canvas.drawBitmap(originalBitmap, 0, 0, null);
			Paint paint = new Paint();
			
			LinearGradient shader = new LinearGradient(width, 0, 0, 0, Color.parseColor(_start), Color.parseColor(_end), Shader.TileMode.CLAMP);
			paint.setShader(shader);
			paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
			canvas.drawRect(0, 0, width, height, paint);
			
			return updatedBitmap;
	}
	
	{
	}
	
	
	public void _TextGradientLibary() {
	}
	private void setTextGradient(TextView view, String color1, String color2, String type) {
		
		if (!(type.equals("TOP_BOTTOM") || (type.equals("BOTTOM_TOP") || (type.equals("LEFT_RIGHT") || type.equals("RIGHT_LEFT"))))) {
				showMessage("Wrong Orientation");
		}
		
		if (type.equals("TOP_BOTTOM")) {
				Shader ts = new LinearGradient(
				0,
				0,
				0,
				view.getPaint().getTextSize(),
				new int[]{
						Color.parseColor(color1),
						Color.parseColor(color2)
				},
				new float[]{
						0,
						1
				}, Shader.TileMode.CLAMP);
				
				view.getPaint().setShader(ts);
		}
		
		if (type.equals("BOTTOM_TOP")) {
				Shader ts = new LinearGradient(
				0,
				0,
				0,
				view.getPaint().getTextSize(),
				new int[]{
						Color.parseColor(color2),
						Color.parseColor(color1)
				},
				new float[]{
						0,
						1
				}, Shader.TileMode.CLAMP);
				
				view.getPaint().setShader(ts);
		}
		
		if (type.equals("LEFT_RIGHT")) {
				TextPaint paint = view.getPaint();
				float width = paint.measureText(view.getText().toString());
				Shader ts = new LinearGradient(
				0,
				0,
				width,
				view.getPaint().getTextSize(),
				new int[]{
						Color.parseColor(color1),
						Color.parseColor(color2)
				},
				new float[]{
						0,
						1
				}, Shader.TileMode.CLAMP);
				
				view.getPaint().setShader(ts);
		}
		
		if (type.equals("RIGHT_LEFT")) {
				TextPaint paint = view.getPaint();
				float width = paint.measureText(view.getText().toString());
				Shader ts = new LinearGradient(
				0,
				0,
				width,
				view.getPaint().getTextSize(),
				new int[]{
						Color.parseColor(color2),
						Color.parseColor(color1)
				},
				new float[]{
						0,
						1
				}, Shader.TileMode.CLAMP);
				
				view.getPaint().setShader(ts);
		}

		return;
	}
	{
	}
	
	
	public void _Gradient(final View _view) {
		android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable(android.graphics.drawable.GradientDrawable.Orientation.RIGHT_LEFT, new int[]{
			0xFF6C64EB,
			0xFFAD81FC
		});
		gd.setCornerRadius(12f);
		_view.setElevation(0f);
		_view.setBackgroundDrawable(gd);
	}
	
	
	public void _changeActivityFont(final String _fontname) {
		fontName = "fonts/".concat(_fontname.concat(".ttf"));
		overrideFonts(this,getWindow().getDecorView()); 
	} 
	private void overrideFonts(final android.content.Context context, final View v) {
		
		try {
			Typeface 
			typeace = Typeface.createFromAsset(getAssets(), fontName);;
			if ((v instanceof ViewGroup)) {
				ViewGroup vg = (ViewGroup) v;
				for (int i = 0;
				i < vg.getChildCount();
				i++) {
					View child = vg.getChildAt(i);
					overrideFonts(context, child);
				}
			}
			else {
				if ((v instanceof TextView)) {
					((TextView) v).setTypeface(typeace);
				}
				else {
					if ((v instanceof EditText )) {
						((EditText) v).setTypeface(typeace);
					}
					else {
						if ((v instanceof Button)) {
							((Button) v).setTypeface(typeace);
						}
					}
				}
			}
		}
		catch(Exception e)
		
		{
			SketchwareUtil.showMessage(getApplicationContext(), "Error Loading Font");
		};
	}
	
	
	public void _Autotyptext() {
	}
	
	public static class AutoTypeTextView extends TextView {
		
		    // Constantes de précision
		    public static int PRECISION_LOW = 8;
		    public static int PRECISION_MED = 9;
		    public static int PRECISION_HIGH = 11;
		    
		    // Paramètres de vitesse
		    private int decryptionSpeed = 10;
		    private int encryptionSpeed = 10;
		    private int typingSpeed = 100;
		    private int precision = 5;
		    
		    // Textes à animer
		    private String animateEncryption = "";
		    private String animateDecryption = "";
		    private String animateTextTyping = "";
		    private String animateTextTypingWithMistakes = "";
		
		    // Gestion de l'animation
		    private Handler handler;
		    private int counter = 0;
		    private boolean misstakeFound = false;
		    private boolean executed = false;
		    private Random ran = new Random();
		    private String misstakeValues = "qwertyuiop[]asdfghjkl;zxcvbnm,./!@#$^&*()_+1234567890";
		    private String encryptedText;
		    private int countLetter = 0;
		    private int cocatation = 0;
		
		    // Gestion des couleurs
		    private final int[] textColors = {
			        0xFFFFA500, // Orange
			        0xFFFFFFFF, // Blanc 
			        0xFF00FF00  // Vert
			    };
		    private int colorChangeInterval;
		    private int currentColorIndex = 0;
		
		    // Constructeurs
		    public AutoTypeTextView(Context context) {
			        super(context);
			        init();
			    }
		
		    public AutoTypeTextView(Context context, AttributeSet attrs) {
			        super(context, attrs);
			        init();
			    }
		
		    private void init() {
			        setGravity(Gravity.CENTER); // Centrage du texte par défaut
					setTypeface(getTypeface(), Typeface.BOLD); // Texte en gras
			    }
		
		    // Configuration automatique
		    private void setupAttributes() {
			        if (animateTextTyping != null) {
				            setTextAutoTyping(animateTextTyping);
				        }
			
			        if (animateTextTypingWithMistakes != null) {
				            if (precision < 6) precision = 6;
				            setTextAutoTypingWithMistakes(animateTextTypingWithMistakes, precision);
				        }
			
			        if (animateDecryption != null) {
				            animateDecryption(animateDecryption);
				        }
			
			        if (animateEncryption != null) {
				            animateEncryption(animateEncryption);
				        }
			    }
		
		    // Calcul de l'intervalle de changement de couleur
		    private void calculateColorInterval(String text) {
			        int minCharsPerColor = 5;
			        int totalColors = textColors.length;
			        this.colorChangeInterval = Math.max(minCharsPerColor, text.length() / totalColors);
			    }
		
		    // Animation de frappe normale
		    public void setTextAutoTyping(final String text) {
			        if (!executed) {
				            calculateColorInterval(text);
				            executed = true;
				            counter = 0;
				            currentColorIndex = 0;
				            setTextColor(textColors[currentColorIndex]);
				            
				            handler = new Handler();
				            handler.postDelayed(new Runnable() {
					                @Override
					                public void run() {
						                    setText(text.substring(0, counter));
						                    
						                    if (counter > 0 && counter % colorChangeInterval == 0) {
							                        currentColorIndex = (currentColorIndex + 1) % textColors.length;
							                        setTextColor(textColors[currentColorIndex]);
							                    }
						                    
						                    counter++;
						                    if (text.length() >= counter) {
							                        postDelayed(this, getTypingSpeed());
							                    } else {
							                        executed = false;
							                    }
						                }
					            }, getTypingSpeed());
				        }
			    }
		
		    // Animation de frappe avec erreurs
		    public void setTextAutoTypingWithMistakes(final String text, final int precision) {
			        if (!executed) {
				            calculateColorInterval(text);
				            executed = true;
				            counter = 0;
				            currentColorIndex = 0;
				            setTextColor(textColors[currentColorIndex]);
				            
				            ran = new Random();
				            handler = new Handler();
				            handler.postDelayed(new Runnable() {
					                @Override
					                public void run() {
						                    if (counter > 0 && counter % colorChangeInterval == 0) {
							                        currentColorIndex = (currentColorIndex + 1) % textColors.length;
							                        setTextColor(textColors[currentColorIndex]);
							                    }
						                    
						                    int num = ran.nextInt(10) + 1;
						                    if (num > precision && counter > 1 && !misstakeFound) {
							                        setText(chooseTypeOfMistake(text, counter));
							                        counter--;
							                    } else {
							                        counter++;
							                        setText(text.substring(0, counter));
							                        misstakeFound = false;
							                    }
						                    
						                    if (text.length() > counter) {
							                        postDelayed(this, getTypingSpeed());
							                    } else {
							                        executed = false;
							                    }
						                }
					            }, getTypingSpeed());
				        }
			    }
		
		    // Animation de décryptage
		    public void animateDecryption(final String text) {
			        encryptedText = text;
			        ran = new Random();
			        handler = new Handler();
			        cocatation = ran.nextInt(10);
			        counter = 0;
			        countLetter = 0;
			        
			        if (!executed) {
				            executed = true;
				            for (int i = 0; i < text.length(); i++) {
					                encryptedText = replaceCharAt(encryptedText, i, 
					                    misstakeValues.charAt(ran.nextInt(misstakeValues.length())));
					                setText(encryptedText);
					            }
				            
				            handler.postDelayed(new Runnable() {
					                @Override
					                public void run() {
						                    if (counter <= cocatation) {
							                        encryptedText = replaceCharAt(encryptedText, countLetter,
							                            misstakeValues.charAt(ran.nextInt(misstakeValues.length())));
							                        setText(encryptedText);
							                        counter++;
							                    } else {
							                        encryptedText = replaceCharAt(encryptedText, countLetter, text.charAt(countLetter));
							                        setText(encryptedText);
							                        countLetter++;
							                        cocatation = ran.nextInt(10);
							                        counter = 0;
							                    }
						                    
						                    if (text.length() > countLetter) {
							                        postDelayed(this, getDecryptionSpeed());
							                    } else {
							                        executed = false;
							                    }
						                }
					            }, getDecryptionSpeed());
				        }
			    }
		
		    // Animation de cryptage
		    public void animateEncryption(final String text) {
			        encryptedText = text;
			        ran = new Random();
			        handler = new Handler();
			        cocatation = ran.nextInt(10);
			        counter = 0;
			        countLetter = 0;
			        
			        if (!executed) {
				            executed = true;
				            handler.postDelayed(new Runnable() {
					                @Override
					                public void run() {
						                    if (counter <= cocatation) {
							                        encryptedText = replaceCharAt(encryptedText, countLetter,
							                            misstakeValues.charAt(ran.nextInt(misstakeValues.length())));
							                        setText(encryptedText);
							                        counter++;
							                    } else {
							                        countLetter++;
							                        cocatation = ran.nextInt(10);
							                        counter = 0;
							                    }
						                    
						                    if (text.length() > countLetter) {
							                        postDelayed(this, getDecryptionSpeed());
							                    } else {
							                        executed = false;
							                    }
						                }
					            }, getDecryptionSpeed());
				        }
			    }
		
		    // Gestion des erreurs de frappe
		    private String chooseTypeOfMistake(String text, int counter) {
			        int misstake = ran.nextInt(3) + 1;
			        String result = text.substring(0, counter);
			        
			        switch (misstake) {
				            case 1:
				                result = text.substring(0, counter - 1) + randomChar();
				                break;
				            case 2:
				                if (ran.nextBoolean()) {
					                    result = text.substring(0, counter - 1) + String.valueOf(text.charAt(counter)).toLowerCase();
					                } else {
					                    result = text.substring(0, counter - 1) + String.valueOf(text.charAt(counter)).toUpperCase();
					                }
				                break;
				            case 3:
				                result = text.substring(0, counter - 1);
				                break;
				        }
			        
			        misstakeFound = true;
			        return result;
			    }
		
		    // Méthodes utilitaires
		    private char randomChar() {
			        return misstakeValues.charAt(ran.nextInt(misstakeValues.length()));
			    }
		
		    public static String replaceCharAt(String text, int pos, char c) {
			        return text.substring(0, pos) + c + text.substring(pos + 1);
			    }
		
		    // Getters et Setters
		    public int getTypingSpeed() {
			        return typingSpeed;
			    }
		
		    public void setTypingSpeed(int typingSpeed) {
			        this.typingSpeed = typingSpeed;
			    }
		
		    public int getDecryptionSpeed() {
			        return decryptionSpeed;
			    }
		
		    public void setDecryptionSpeed(int decryptionSpeed) {
			        this.decryptionSpeed = decryptionSpeed;
			    }
		
		    public int getEncryptionSpeed() {
			        return encryptionSpeed;
			    }
		
		    public void setEncryptionSpeed(int encryptionSpeed) {
			        this.encryptionSpeed = encryptionSpeed;
			    }
		
		    public boolean isRunning() {
			        return executed;
			    }
	}
	{
	}
	
	
	public void _AutotypText2() {
	}
	
	public static class AutoTypeTextView1 extends TextView {
		
		    // Constantes de précision
		    public static int PRECISION_LOW = 8;
		    public static int PRECISION_MED = 9;
		    public static int PRECISION_HIGH = 11;
		    
		    // Paramètres de vitesse
		    private int decryptionSpeed = 10;
		    private int encryptionSpeed = 10;
		    private int typingSpeed = 100;
		    private int precision = 5;
		    
		    // Textes à animer
		    private String animateEncryption = "";
		    private String animateDecryption = "";
		    private String animateTextTyping = "";
		    private String animateTextTypingWithMistakes = "";
		
		    // Gestion de l'animation
		    private Handler handler;
		    private int counter = 0;
		    private boolean misstakeFound = false;
		    private boolean executed = false;
		    private Random ran = new Random();
		    private String misstakeValues = "qwertyuiop[]asdfghjkl;zxcvbnm,./!@#$^&*()_+1234567890";
		    private String encryptedText;
		    private int countLetter = 0;
		    private int cocatation = 0;
		
		    // Gestion des couleurs
		    private final int[] textColors = {
			        0xFF00FF00, // Orange
			        0xFFF1F790, // Blanc 
			        0xFF00FF00  // Vert
			    };
		    private int colorChangeInterval;
		    private int currentColorIndex = 0;
		
		    // Constructeurs
		    public AutoTypeTextView1(Context context) {
			        super(context);
			        init();
			    }
		
		    public AutoTypeTextView1(Context context, AttributeSet attrs) {
			        super(context, attrs);
			        init();
			    }
		
		    private void init() {
			        setGravity(Gravity.CENTER); // Centrage du texte par défaut
					setTypeface(getTypeface(), Typeface.BOLD); // Texte en gras
			    }
		
		    // Configuration automatique
		    private void setupAttributes() {
			        if (animateTextTyping != null) {
				            setTextAutoTyping(animateTextTyping);
				        }
			
			        if (animateTextTypingWithMistakes != null) {
				            if (precision < 6) precision = 6;
				            setTextAutoTypingWithMistakes(animateTextTypingWithMistakes, precision);
				        }
			
			        if (animateDecryption != null) {
				            animateDecryption(animateDecryption);
				        }
			
			        if (animateEncryption != null) {
				            animateEncryption(animateEncryption);
				        }
			    }
		
		    // Calcul de l'intervalle de changement de couleur
		    private void calculateColorInterval(String text) {
			        int minCharsPerColor = 5;
			        int totalColors = textColors.length;
			        this.colorChangeInterval = Math.max(minCharsPerColor, text.length() / totalColors);
			    }
		
		    // Animation de frappe normale
		    public void setTextAutoTyping(final String text) {
			        if (!executed) {
				            calculateColorInterval(text);
				            executed = true;
				            counter = 0;
				            currentColorIndex = 0;
				            setTextColor(textColors[currentColorIndex]);
				            
				            handler = new Handler();
				            handler.postDelayed(new Runnable() {
					                @Override
					                public void run() {
						                    setText(text.substring(0, counter));
						                    
						                    if (counter > 0 && counter % colorChangeInterval == 0) {
							                        currentColorIndex = (currentColorIndex + 1) % textColors.length;
							                        setTextColor(textColors[currentColorIndex]);
							                    }
						                    
						                    counter++;
						                    if (text.length() >= counter) {
							                        postDelayed(this, getTypingSpeed());
							                    } else {
							                        executed = false;
							                    }
						                }
					            }, getTypingSpeed());
				        }
			    }
		
		    // Animation de frappe avec erreurs
		    public void setTextAutoTypingWithMistakes(final String text, final int precision) {
			        if (!executed) {
				            calculateColorInterval(text);
				            executed = true;
				            counter = 0;
				            currentColorIndex = 0;
				            setTextColor(textColors[currentColorIndex]);
				            
				            ran = new Random();
				            handler = new Handler();
				            handler.postDelayed(new Runnable() {
					                @Override
					                public void run() {
						                    if (counter > 0 && counter % colorChangeInterval == 0) {
							                        currentColorIndex = (currentColorIndex + 1) % textColors.length;
							                        setTextColor(textColors[currentColorIndex]);
							                    }
						                    
						                    int num = ran.nextInt(10) + 1;
						                    if (num > precision && counter > 1 && !misstakeFound) {
							                        setText(chooseTypeOfMistake(text, counter));
							                        counter--;
							                    } else {
							                        counter++;
							                        setText(text.substring(0, counter));
							                        misstakeFound = false;
							                    }
						                    
						                    if (text.length() > counter) {
							                        postDelayed(this, getTypingSpeed());
							                    } else {
							                        executed = false;
							                    }
						                }
					            }, getTypingSpeed());
				        }
			    }
		
		    // Animation de décryptage
		    public void animateDecryption(final String text) {
			        encryptedText = text;
			        ran = new Random();
			        handler = new Handler();
			        cocatation = ran.nextInt(10);
			        counter = 0;
			        countLetter = 0;
			        
			        if (!executed) {
				            executed = true;
				            for (int i = 0; i < text.length(); i++) {
					                encryptedText = replaceCharAt(encryptedText, i, 
					                    misstakeValues.charAt(ran.nextInt(misstakeValues.length())));
					                setText(encryptedText);
					            }
				            
				            handler.postDelayed(new Runnable() {
					                @Override
					                public void run() {
						                    if (counter <= cocatation) {
							                        encryptedText = replaceCharAt(encryptedText, countLetter,
							                            misstakeValues.charAt(ran.nextInt(misstakeValues.length())));
							                        setText(encryptedText);
							                        counter++;
							                    } else {
							                        encryptedText = replaceCharAt(encryptedText, countLetter, text.charAt(countLetter));
							                        setText(encryptedText);
							                        countLetter++;
							                        cocatation = ran.nextInt(10);
							                        counter = 0;
							                    }
						                    
						                    if (text.length() > countLetter) {
							                        postDelayed(this, getDecryptionSpeed());
							                    } else {
							                        executed = false;
							                    }
						                }
					            }, getDecryptionSpeed());
				        }
			    }
		
		    // Animation de cryptage
		    public void animateEncryption(final String text) {
			        encryptedText = text;
			        ran = new Random();
			        handler = new Handler();
			        cocatation = ran.nextInt(10);
			        counter = 0;
			        countLetter = 0;
			        
			        if (!executed) {
				            executed = true;
				            handler.postDelayed(new Runnable() {
					                @Override
					                public void run() {
						                    if (counter <= cocatation) {
							                        encryptedText = replaceCharAt(encryptedText, countLetter,
							                            misstakeValues.charAt(ran.nextInt(misstakeValues.length())));
							                        setText(encryptedText);
							                        counter++;
							                    } else {
							                        countLetter++;
							                        cocatation = ran.nextInt(10);
							                        counter = 0;
							                    }
						                    
						                    if (text.length() > countLetter) {
							                        postDelayed(this, getDecryptionSpeed());
							                    } else {
							                        executed = false;
							                    }
						                }
					            }, getDecryptionSpeed());
				        }
			    }
		
		    // Gestion des erreurs de frappe
		    private String chooseTypeOfMistake(String text, int counter) {
			        int misstake = ran.nextInt(3) + 1;
			        String result = text.substring(0, counter);
			        
			        switch (misstake) {
				            case 1:
				                result = text.substring(0, counter - 1) + randomChar();
				                break;
				            case 2:
				                if (ran.nextBoolean()) {
					                    result = text.substring(0, counter - 1) + String.valueOf(text.charAt(counter)).toLowerCase();
					                } else {
					                    result = text.substring(0, counter - 1) + String.valueOf(text.charAt(counter)).toUpperCase();
					                }
				                break;
				            case 3:
				                result = text.substring(0, counter - 1);
				                break;
				        }
			        
			        misstakeFound = true;
			        return result;
			    }
		
		    // Méthodes utilitaires
		    private char randomChar() {
			        return misstakeValues.charAt(ran.nextInt(misstakeValues.length()));
			    }
		
		    public static String replaceCharAt(String text, int pos, char c) {
			        return text.substring(0, pos) + c + text.substring(pos + 1);
			    }
		
		    // Getters et Setters
		    public int getTypingSpeed() {
			        return typingSpeed;
			    }
		
		    public void setTypingSpeed(int typingSpeed) {
			        this.typingSpeed = typingSpeed;
			    }
		
		    public int getDecryptionSpeed() {
			        return decryptionSpeed;
			    }
		
		    public void setDecryptionSpeed(int decryptionSpeed) {
			        this.decryptionSpeed = decryptionSpeed;
			    }
		
		    public int getEncryptionSpeed() {
			        return encryptionSpeed;
			    }
		
		    public void setEncryptionSpeed(int encryptionSpeed) {
			        this.encryptionSpeed = encryptionSpeed;
			    }
		
		    public boolean isRunning() {
			        return executed;
			    }
	}
	{
	}
	
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	
	@Deprecated
	public int getLocationX(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[0];
	}
	
	@Deprecated
	public int getLocationY(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[1];
	}
	
	@Deprecated
	public int getRandom(int _min, int _max) {
		Random random = new Random();
		return random.nextInt(_max - _min + 1) + _min;
	}
	
	@Deprecated
	public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
		ArrayList<Double> _result = new ArrayList<Double>();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
			_result.add((double)_arr.keyAt(_iIdx));
		}
		return _result;
	}
	
	@Deprecated
	public float getDip(int _input) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}
	
	@Deprecated
	public int getDisplayWidthPixels() {
		return getResources().getDisplayMetrics().widthPixels;
	}
	
	@Deprecated
	public int getDisplayHeightPixels() {
		return getResources().getDisplayMetrics().heightPixels;
	}
}

