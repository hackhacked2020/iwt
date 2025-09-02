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
import java.util.HashMap;
import java.util.ArrayList;
import android.widget.LinearLayout;
import android.webkit.WebView;
import android.webkit.WebSettings;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.Intent;
import android.net.Uri;
import java.util.Timer;
import java.util.TimerTask;
import android.animation.ObjectAnimator;
import android.view.animation.LinearInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ChildEventListener;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;

public class HomeActivity extends AppCompatActivity {
	
	private Timer _timer = new Timer();
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private HashMap<String, Object> map = new HashMap<>();
	private double count_ = 0;
	private String test = "";
	private String package_name = "";
	private String recent_version = "";
	private String description = "";
	private String ver = "";
	private String url = "";
	
	private ArrayList<HashMap<String, Object>> Map = new ArrayList<>();
	
	private LinearLayout linear1;
	private WebView webview1;
	
	private SharedPreferences keyStore;
	private Intent next = new Intent();
	private SharedPreferences count;
	private TimerTask t1;
	private ObjectAnimator ob = new ObjectAnimator();
	private DatabaseReference update = _firebase.getReference("update");
	private ChildEventListener _update_child_listener;
	private Intent update_intent = new Intent();
	private Intent intent = new Intent();
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.home);
		initialize(_savedInstanceState);
		com.google.firebase.FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		linear1 = findViewById(R.id.linear1);
		webview1 = findViewById(R.id.webview1);
		webview1.getSettings().setJavaScriptEnabled(true);
		webview1.getSettings().setSupportZoom(true);
		keyStore = getSharedPreferences("keyStore", Activity.MODE_PRIVATE);
		count = getSharedPreferences("count", Activity.MODE_PRIVATE);
		
		webview1.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageStarted(WebView _param1, String _param2, Bitmap _param3) {
				final String _url = _param2;
				
				super.onPageStarted(_param1, _param2, _param3);
			}
			
			@Override
			public void onPageFinished(WebView _param1, String _param2) {
				final String _url = _param2;
				
				super.onPageFinished(_param1, _param2);
			}
		});
		
		_update_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				update.addListenerForSingleValueEvent(new ValueEventListener() {
					    @Override
					    public void onDataChange(DataSnapshot _dataSnapshot) {
						        Map = new ArrayList<>();
						        try {
							            GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							            for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								                HashMap<String, Object> _map = _data.getValue(_ind);
								                Map.add(_map);
								            }
							        } catch (Exception _e) {
							            _e.printStackTrace();
							            return;
							        }
						
						        if (Map.isEmpty()) {
							            showMessage("No update data found!");
							            return;
							        }
						
						        recent_version = Map.get(0).get("recent_version").toString();
						        description = Map.get(0).get("description").toString();
						
						        if (ver.equals(recent_version)) {
							            // Pas de mise à jour
							        } else if (ver.compareTo(recent_version) < 0) {
							            final AlertDialog dialog1 = new AlertDialog.Builder(HomeActivity.this).create();
							            View inflate = getLayoutInflater().inflate(R.layout.custom, null); 
							            dialog1.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
							            dialog1.setView(inflate);
							
							            TextView version = inflate.findViewById(R.id.version);
							            TextView whats_new = inflate.findViewById(R.id.whats_new);
							
							            version.setText("Version : " + recent_version);
							            whats_new.setText(description); // Utilisez la variable `description`
							            if (!isFinishing() && !isDestroyed()) {
								    dialog1.show();
							}
							        }
						    }
					
					    @Override
					    public void onCancelled(DatabaseError _databaseError) {
						        showMessage("Database error: " + _databaseError.getMessage());
						    }
				});
				description = _childValue.get("description").toString();
				url = _childValue.get("url").toString();
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
		update.addChildEventListener(_update_child_listener);
	}
	
	private void initializeLogic() {
		/*String savedKey = keyStore.getString("Key", "");

if (savedKey.isEmpty()) {
    
} else {
    // Arrêter le timer si savedKey n'est pas vide et que le timer est actif
    if (t1 != null) {
        t1.cancel();  // Annule la tâche du timer
        _timer.purge(); // Supprime les tâches annulées de la file d'attente
    }
}*/
		// Configuration initiale du WebView
		webview1.getSettings().setAllowFileAccess(true);
		webview1.getSettings().setAllowContentAccess(true);
		webview1.getSettings().setAllowUniversalAccessFromFileURLs(true);
		webview1.getSettings().setAllowFileAccessFromFileURLs(true);
		webview1.getSettings().setJavaScriptEnabled(true);
		webview1.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		webview1.getSettings().setDomStorageEnabled(true);
		webview1.getSettings().setSaveFormData(true);
		webview1.getSettings().setLoadWithOverviewMode(true); 
		webview1.getSettings().setUseWideViewPort(true); 
		final WebSettings webSettings = webview1.getSettings(); 
		final String newUserAgent = "Mozilla/5.0 (Android) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36"; 
		webSettings.setUserAgentString(newUserAgent);
		setDesktopMode(webview1, false);
		
		// WebViewClient pour gérer les liens
		webview1.setWebViewClient(new WebViewClient() { 
			    public void onPageFinished(WebView view, String url) {
				        // Logique post-chargement si nécessaire
				    }
			    
			    @Override
			    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
				        String url = request.getUrl().toString();
				        
				        if (url.startsWith("whatsapp://")) {
					            try {
						                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
						                startActivity(intent);
						                return true;
						            } catch (Exception e) {
						                view.loadUrl(url.replace("whatsapp://", "https://whatsapp.com/"));
						                return true;
						            }
					        }
				        
				        if (url.startsWith("https://wa.me/")) {
					            try {
						                Intent intent = new Intent(Intent.ACTION_VIEW);
						                intent.setData(Uri.parse(url));
						                startActivity(intent);
						                return true;
						            } catch (Exception e) {
						                view.loadUrl(url);
						                return true;
						            }
					        }
				        
				        if (url.startsWith("tel:")) {
					            try {
						                Intent intent = new Intent(Intent.ACTION_DIAL);
						                intent.setData(Uri.parse(url));
						                startActivity(intent);
						                return true;
						            } catch (Exception e) {
						                e.printStackTrace();
						                return true;
						            }
					        }
				        
				        if (url.startsWith("https://classification-des-medicaments")) {
					            try {
						                Intent intent = new Intent(Intent.ACTION_VIEW);
						                intent.setData(Uri.parse(url));
						                startActivity(intent);
						                return true;
						            } catch (Exception e) {
						                view.loadUrl(url);
						                return true;
						            }
					        }
				        
				        if (url.startsWith("https://drive.google.com/") || url.startsWith("https://docs.google.com/")) {
					            try {
						                Intent intent = new Intent(Intent.ACTION_VIEW);
						                intent.setData(Uri.parse(url));
						                intent.setPackage("com.google.android.apps.docs");
						                startActivity(intent);
						                return true;
						            } catch (Exception e) {
						                view.loadUrl(url);
						                return true;
						            }
					        }
				        
				        return false;
				    }
		});
		
		// Chargement de la page
		webview1.loadUrl("file:///android_asset/index.html");
		webview1.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		
		// Vérification de la clé
		//String savedKey = keyStore.getString("Key", "");
		//if (savedKey.isEmpty()) {
		 if (!"actif".equals(keyStore.getString("key", ""))) {
			   t1 = new TimerTask() {
				        @Override
				        public void run() {
					            runOnUiThread(new Runnable() {
						                @Override
						                public void run() {
							                    _error1();
							                }
						            });
					        }
				    };
			    _timer.schedule(t1, (int)(30000));
		} else {
			    // Arrêter le timer si savedKey n'est pas vide et que le timer est actif
			    if (t1 != null) {
				        t1.cancel();  // Annule la tâche du timer
				        _timer.purge(); // Supprime les tâches annulées de la file d'attente
				    }
		}
		
		// Gestion de la mise à jour
		package_name = "dcmchonorable.iwt";
		try {
			    android.content.pm.PackageInfo pinfo = getPackageManager().getPackageInfo(package_name, 0);
			    ver = pinfo.versionName;
			    if (ver == null || ver.isEmpty()) {
				        showMessage("Erreur : versionName est vide !");
				        return;
				    }
		} catch (Exception e) {
			    showMessage("Erreur : " + e.getMessage());
			    return;
		}
		
		/*if (recent_version == null || recent_version.isEmpty()) {
    showMessage("Erreur : recent_version est vide !");
    return;
}*/
		
		// Comparaison des versions
		if (ver.compareTo(recent_version) < 0) {
			    final AlertDialog updateDialog = new AlertDialog.Builder(HomeActivity.this).create();
			    LayoutInflater inflater = getLayoutInflater();
			    View convertView = inflater.inflate(R.layout.custom, null);
			    
			    // Configuration du dialogue
			    updateDialog.setView(convertView);
			    updateDialog.setCancelable(false);
			    updateDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			    updateDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
			
			    // Récupération des vues avec convertView.findViewById()
			    LinearLayout s_bg = (LinearLayout) convertView.findViewById(R.id.bg);
			    TextView version = (TextView) convertView.findViewById(R.id.version);
			    TextView whats_new = (TextView) convertView.findViewById(R.id.whats_new);
			    TextView now = (TextView) convertView.findViewById(R.id.now);
			
			    // Style personnalisé
			    android.graphics.drawable.GradientDrawable ad = new android.graphics.drawable.GradientDrawable();
			    ad.setColor(Color.parseColor("#4CAF50")); // Couleur verte
			    ad.setCornerRadius(25);
			    s_bg.setBackground(ad);
			
			    // Contenu
			    version.setText("Version : " + recent_version);
			    whats_new.setText(description);
			
			    // Gestion du clic sur "now"
			    now.setOnClickListener(new View.OnClickListener() {
				        @Override
				        public void onClick(View v) {
					            try {
						                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
						                startActivity(browserIntent);
						                updateDialog.dismiss();
						            } catch (Exception e) {
						                Toast.makeText(HomeActivity.this, "Impossible d'ouvrir le lien", Toast.LENGTH_SHORT).show();
						            }
					        }
				    });
			
			    if (!isFinishing() && !isDestroyed()) {
				        updateDialog.show();
				    }
		}
		
		
	}
	// Méthode setDesktopMode (externe)
	public void setDesktopMode(WebView webview1, boolean enabled) { 
		    String newUserAgent = webview1.getSettings().getUserAgentString(); 
		    if (enabled) { 
			        try { 
				            String ua = webview1.getSettings().getUserAgentString(); 
				            String androidOSString = ua.substring(ua.indexOf("("), ua.indexOf(")") + 1); 
				            newUserAgent = ua.replace(androidOSString, "(X11; Linux x86_64)"); 
				        } catch (Exception e) { 
				            e.printStackTrace(); 
				        } 
			    } else { 
			        newUserAgent = null; 
			    } 
		    webview1.getSettings().setUserAgentString(newUserAgent); 
		    webview1.getSettings().setUseWideViewPort(enabled); 
		    webview1.getSettings().setLoadWithOverviewMode(enabled); 
		    webview1.reload();
	}
	{
	}
	
	@Override
	public void onBackPressed() {
		if (webview1.canGoBack()) {
			webview1.goBack();
		}
	}
	
	@Override
	public void onStart() {
		super.onStart();
		long currentValue = count.getLong("count", 0L);
		
		// Vérifier d'abord, puis incrémenter
		if (currentValue >= 3) {  // Bloquer à partir de la 4ème ouverture
			    if (!"actif".equals(keyStore.getString("key", ""))) {
						
						
						    // Arrêter le timer si savedKey n'est pas vide et que le timer est actif
				    if (t1 != null) {
					        t1.cancel();  // Annule la tâche du timer
					        _timer.purge(); // Supprime les tâches annulées de la file d'attente
					    }
						
						
				        next.setClass(getApplicationContext(), LockedActivity.class);
				        startActivity(next);
				        finish(); // Éviter de revenir à HomeActivity
				        return;   // Arrêter l'exécution
				    }
		} else {
				// Si < 4, incrémenter et aller à HomeActivity
			count.edit().putLong("count", currentValue + 1).apply();
		}
	}
	public void _error1() {
		final AlertDialog error1 = new AlertDialog.Builder(HomeActivity.this).create(); LayoutInflater inflater = getLayoutInflater();
		View convertView = (View) inflater.inflate(R.layout.error1, null); error1.setView(convertView);
		error1.setCancelable(false); error1.requestWindowFeature(Window.FEATURE_NO_TITLE); error1.getWindow().setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(Color.TRANSPARENT)); LinearLayout s_bg = (LinearLayout) convertView.findViewById(R.id.linear_bg);
		LinearLayout b_con = (LinearLayout) convertView.findViewById(R.id.linear_content); Button b_ok = (Button) convertView.findViewById(R.id.button1); ImageView b_img = (ImageView) convertView.findViewById(R.id.imageview1);
		android.graphics.drawable.GradientDrawable ad = new android.graphics.drawable.GradientDrawable(); ad.setColor(Color.parseColor("#F44336")); ad.setCornerRadius(25); b_con.setBackground(ad); android.graphics.drawable.GradientDrawable ag = new android.graphics.drawable.GradientDrawable(); ag.setColor(Color.parseColor("#FFFFFF")); ag.setCornerRadius(50); b_ok.setBackground(ag); b_img.setElevation(5); b_ok.setOnClickListener(new View.OnClickListener(){ public void onClick(View v){ 
					
				error1.dismiss();
				     finish();
				 
				  }}); error1.show(); _rotate(b_img);
	}
	
	
	public void _rotate(final View _view) {
		ob.setTarget(_view);
		ob.setPropertyName("rotation");
		ob.setFloatValues((float)(-60), (float)(0));
		ob.setDuration((int)(1000));
		ob.setInterpolator(new BounceInterpolator());
		ob.start();
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