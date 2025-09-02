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
import android.widget.TextView;
import android.widget.ScrollView;
import android.widget.EditText;
import com.google.android.material.button.*;
import de.hdodenhof.circleimageview.*;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ChildEventListener;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.Intent;
import android.net.Uri;
import android.animation.ObjectAnimator;
import android.view.animation.LinearInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;

public class LockedActivity extends AppCompatActivity {
	
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private String ID = "";
	private HashMap<String, Object> map = new HashMap<>();
	
	private ArrayList<HashMap<String, Object>> listmap = new ArrayList<>();
	
	private LinearLayout linear27;
	private LinearLayout linear1;
	private LinearLayout linear9;
	private LinearLayout linear2;
	private LinearLayout linear14;
	private LinearLayout linear3;
	private LinearLayout linear4;
	private LinearLayout linear5;
	private TextView textview3;
	private TextView textview4;
	private ScrollView vscroll1;
	private LinearLayout linear16;
	private LinearLayout linear17;
	private LinearLayout linear18;
	private LinearLayout linear34;
	private LinearLayout linear35;
	private TextView textview18;
	private LinearLayout linear6;
	private EditText search;
	private MaterialButton materialbutton1;
	private TextView textview19;
	private LinearLayout linear10;
	private CircleImageView circleimageview1;
	
	private DatabaseReference fdb = _firebase.getReference("fdb");
	private ChildEventListener _fdb_child_listener;
	private SharedPreferences keyStore;
	private Intent next = new Intent();
	private ObjectAnimator ob = new ObjectAnimator();
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.locked);
		initialize(_savedInstanceState);
		com.google.firebase.FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		linear27 = findViewById(R.id.linear27);
		linear1 = findViewById(R.id.linear1);
		linear9 = findViewById(R.id.linear9);
		linear2 = findViewById(R.id.linear2);
		linear14 = findViewById(R.id.linear14);
		linear3 = findViewById(R.id.linear3);
		linear4 = findViewById(R.id.linear4);
		linear5 = findViewById(R.id.linear5);
		textview3 = findViewById(R.id.textview3);
		textview4 = findViewById(R.id.textview4);
		vscroll1 = findViewById(R.id.vscroll1);
		linear16 = findViewById(R.id.linear16);
		linear17 = findViewById(R.id.linear17);
		linear18 = findViewById(R.id.linear18);
		linear34 = findViewById(R.id.linear34);
		linear35 = findViewById(R.id.linear35);
		textview18 = findViewById(R.id.textview18);
		linear6 = findViewById(R.id.linear6);
		search = findViewById(R.id.search);
		materialbutton1 = findViewById(R.id.materialbutton1);
		textview19 = findViewById(R.id.textview19);
		linear10 = findViewById(R.id.linear10);
		circleimageview1 = findViewById(R.id.circleimageview1);
		keyStore = getSharedPreferences("keyStore", Activity.MODE_PRIVATE);
		
		materialbutton1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				/*
if (search.getText().toString().equals("")) {
    SketchwareUtil.showMessage(getApplicationContext(), "Veuillez Entrer un code d'accès...");
}
else {
    if (SketchwareUtil.isConnected(getApplicationContext())) {
        if (listmap.get((int)0).containsKey(search.getText().toString())) {
            if (listmap.get((int)0).get(search.getText().toString()).toString().equals("ID")) {
                map = new HashMap<>();
                map.put(search.getText().toString(), ID);
                fdb.child("KEYS").updateChildren(map);
                // Mise à jour locale immédiate de listmap
                listmap.get(0).put(search.getText().toString(), ID);
                map.clear();
                keyStore.edit().putString("key", "actif").commit();
                keyStore.edit().putString("userKey", search.getText().toString()).commit();
                _success();
            }
            else {
                if (ID.equals(listmap.get((int)0).get(search.getText().toString()).toString())) {
                    keyStore.edit().putString("key", "actif").commit();
                    keyStore.edit().putString("userKey", search.getText().toString()).commit();
                    _success();
                }
                else {
                    _error();
                }
            }
        }
        else {
            _error3();
        }
    }
    else {
        SketchwareUtil.showMessage(getApplicationContext(), "Veuillez activer la connexion internet pour valider votre code d'accès.");
    }
}
*/
				if (search.getText().toString().equals("")) {
					SketchwareUtil.showMessage(getApplicationContext(), "Veuillez Entrer un code d'accès...");
				}
				else {
					if (SketchwareUtil.isConnected(getApplicationContext())) {
						if (listmap.get((int)0).containsKey(search.getText().toString())) {
							if (listmap.get((int)0).get(search.getText().toString()).toString().equals("ID")) {
								map = new HashMap<>();
								map.put(search.getText().toString(), ID);
								fdb.child("KEYS").updateChildren(map);
								map.clear();
								keyStore.edit().putString("key", "actif").commit();
								keyStore.edit().putString("userKey", search.getText().toString()).commit();
								_success();
							}
							else {
								if (ID.equals(listmap.get((int)0).get(search.getText().toString()).toString())) {
									keyStore.edit().putString("key", "actif").commit();
									keyStore.edit().putString("userKey", search.getText().toString()).commit();
									_success();
								}
								else {
									_error();
								}
							}
						}
						else {
							_error3();
						}
					}
					else {
						SketchwareUtil.showMessage(getApplicationContext(), "Veuillez activer la connexion internet pour valider votre code d'accès.");
					}
				}
			}
		});
		
		linear10.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				next.setAction(Intent.ACTION_VIEW);
				next.setData(Uri.parse("https://wa.me/2250160336129?text=bonjour%20veillez%20me%20donner%20un%20code%20d%27accès%20IWT%20svp"));
				startActivity(next);
			}
		});
		
		circleimageview1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				next.setAction(Intent.ACTION_VIEW);
				next.setData(Uri.parse("https://wa.me/2250160336129?text=bonjour%20veillez%20me%20donner%20un%20code%20d%27accès%20IWT%20svp"));
				startActivity(next);
			}
		});
		
		_fdb_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				fdb.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						listmap = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								listmap.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						if (listmap.size() == 0) {
							_aProGress(false);
						}
						else {
							_aProGress(false);
							if (keyStore.getString("Key", "").equals("")) {
								
							}
							else {
								if (listmap.get((int)0).containsKey(keyStore.getString("Key", ""))) {
									if (listmap.get((int)0).get(keyStore.getString("Key", "")).toString().equals(ID)) {
										
									}
									else {
										SketchwareUtil.showMessage(getApplicationContext(), "Ce FreeCode est deja utilisé par quelqu'un !");
									}
								}
								else {
									SketchwareUtil.showMessage(getApplicationContext(), "Ce FreeCode a expiré !");
								}
							}
						}
					}
					@Override
					public void onCancelled(DatabaseError _databaseError) {
					}
				});
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
		fdb.addChildEventListener(_fdb_child_listener);
	}
	
	private void initializeLogic() {
		ID = android.os.Build.ID ;
	}
	
	@Override
	public void onBackPressed() {
		
	}
	public void _aProGress(final boolean _ifShow) {
		if (_ifShow) {
			if (prog == null){
				prog = new ProgressDialog(this);
				prog.setCancelable(false);
				prog.setCanceledOnTouchOutside(false);
				
				prog.requestWindowFeature(Window.FEATURE_NO_TITLE);  prog.getWindow().setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(Color.TRANSPARENT));
				
			}
			prog.setMessage(null);
			prog.show();
			prog.setContentView(R.layout.cus);
		}
		else {
			if (prog != null){
				prog.dismiss();
			}
		}
	}
	private ProgressDialog prog;
	{
	}
	
	
	public void _encrypt() {
	}
	public String EcryptingTheTextMethod(final String _string, final String _key) {
				try{
			javax.crypto.SecretKey key = generateKey(_key);
			javax.crypto.Cipher c = javax.crypto.Cipher.getInstance("AES");
			c.init(javax.crypto.Cipher.ENCRYPT_MODE, key);
			byte[] encVal = c.doFinal(_string.getBytes());
			return android.util.Base64.encodeToString(encVal,android.util.Base64.DEFAULT);
				} catch (Exception e) {
				}
		return "";
		}
	
		public String DecryptingTheTextMethod(final String _string, final String _key) {
				try {
			javax.crypto.spec.SecretKeySpec key = (javax.crypto.spec.SecretKeySpec) generateKey(_key);
			javax.crypto.Cipher c = javax.crypto.Cipher.getInstance("AES");
			c.init(javax.crypto.Cipher.DECRYPT_MODE,key);
			byte[] decode = android.util.Base64.decode(_string,android.util.Base64.DEFAULT);
			byte[] decval = c.doFinal(decode);
			return new String(decval);
				} catch (Exception ex) {
				}
		return "";
		}
		public static javax.crypto.SecretKey generateKey(String pwd) throws Exception {
		final java.security.MessageDigest digest = java.security.MessageDigest.getInstance("SHA-256");
		byte[] b = pwd.getBytes("UTF-8");
		digest.update(b,0,b.length);
		byte[] key = digest.digest();
		javax.crypto.spec.SecretKeySpec sec = new javax.crypto.spec.SecretKeySpec(key, "AES");
		return sec;
		}
	{
	}
	
	
	public void _error() {
		final AlertDialog error2 = new AlertDialog.Builder(LockedActivity.this).create(); LayoutInflater inflater = getLayoutInflater();
		View convertView = (View) inflater.inflate(R.layout.error2, null); error2.setView(convertView);
		error2.setCancelable(false); error2.requestWindowFeature(Window.FEATURE_NO_TITLE); error2.getWindow().setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(Color.TRANSPARENT)); LinearLayout s_bg = (LinearLayout) convertView.findViewById(R.id.linear_bg);
		LinearLayout b_con = (LinearLayout) convertView.findViewById(R.id.linear_content); Button b_ok = (Button) convertView.findViewById(R.id.button1); ImageView b_img = (ImageView) convertView.findViewById(R.id.imageview1);
		android.graphics.drawable.GradientDrawable ad = new android.graphics.drawable.GradientDrawable(); ad.setColor(Color.parseColor("#F44336")); ad.setCornerRadius(25); b_con.setBackground(ad); android.graphics.drawable.GradientDrawable ag = new android.graphics.drawable.GradientDrawable(); ag.setColor(Color.parseColor("#FFFFFF")); ag.setCornerRadius(50); b_ok.setBackground(ag); b_img.setElevation(5); b_ok.setOnClickListener(new View.OnClickListener(){ public void onClick(View v){ 
						
				 error2.dismiss();
				 
				  }}); error2.show(); _rotate(b_img);
	}
	
	
	public void _error1() {
		final AlertDialog error1 = new AlertDialog.Builder(LockedActivity.this).create(); LayoutInflater inflater = getLayoutInflater();
		View convertView = (View) inflater.inflate(R.layout.error1, null); error1.setView(convertView);
		error1.setCancelable(false); error1.requestWindowFeature(Window.FEATURE_NO_TITLE); error1.getWindow().setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(Color.TRANSPARENT)); LinearLayout s_bg = (LinearLayout) convertView.findViewById(R.id.linear_bg);
		LinearLayout b_con = (LinearLayout) convertView.findViewById(R.id.linear_content); Button b_ok = (Button) convertView.findViewById(R.id.button1); ImageView b_img = (ImageView) convertView.findViewById(R.id.imageview1);
		android.graphics.drawable.GradientDrawable ad = new android.graphics.drawable.GradientDrawable(); ad.setColor(Color.parseColor("#F44336")); ad.setCornerRadius(25); b_con.setBackground(ad); android.graphics.drawable.GradientDrawable ag = new android.graphics.drawable.GradientDrawable(); ag.setColor(Color.parseColor("#FFFFFF")); ag.setCornerRadius(50); b_ok.setBackground(ag); b_img.setElevation(5); b_ok.setOnClickListener(new View.OnClickListener(){ public void onClick(View v){ 
					
				error1.dismiss();
				 
				  }}); error1.show(); _rotate(b_img);
	}
	
	
	public void _error3() {
		final AlertDialog error3 = new AlertDialog.Builder(LockedActivity.this).create(); LayoutInflater inflater = getLayoutInflater();
		View convertView = (View) inflater.inflate(R.layout.error3, null); error3.setView(convertView);
		error3.setCancelable(false); error3.requestWindowFeature(Window.FEATURE_NO_TITLE); error3.getWindow().setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(Color.TRANSPARENT)); LinearLayout s_bg = (LinearLayout) convertView.findViewById(R.id.linear_bg);
		LinearLayout b_con = (LinearLayout) convertView.findViewById(R.id.linear_content); Button b_ok = (Button) convertView.findViewById(R.id.button1); ImageView b_img = (ImageView) convertView.findViewById(R.id.imageview1);
		android.graphics.drawable.GradientDrawable ad = new android.graphics.drawable.GradientDrawable(); ad.setColor(Color.parseColor("#F44336")); ad.setCornerRadius(25); b_con.setBackground(ad); android.graphics.drawable.GradientDrawable ag = new android.graphics.drawable.GradientDrawable(); ag.setColor(Color.parseColor("#FFFFFF")); ag.setCornerRadius(50); b_ok.setBackground(ag); b_img.setElevation(5); b_ok.setOnClickListener(new View.OnClickListener(){ public void onClick(View v){ 
						
				 error3.dismiss();
				 
				  }}); error3.show(); _rotate(b_img);
	}
	
	
	public void _rotate(final View _view) {
		ob.setTarget(_view);
		ob.setPropertyName("rotation");
		ob.setFloatValues((float)(-60), (float)(0));
		ob.setDuration((int)(1000));
		ob.setInterpolator(new BounceInterpolator());
		ob.start();
	}
	
	
	public void _success() {
		final AlertDialog success = new AlertDialog.Builder(LockedActivity.this).create();
		LayoutInflater inflater = getLayoutInflater();
		View convertView = (View) inflater.inflate(R.layout.success, null);
		success.setView(convertView);
		success.setCancelable(false);
		success.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		Button b_ok = (Button) convertView.findViewById(R.id.button1);
		ImageView b_img = (ImageView) convertView.findViewById(R.id.imageview1);
		
		b_ok.setOnClickListener(new View.OnClickListener() {
			    public void onClick(View v) {
				        success.dismiss();
				        next.setClass(getApplicationContext(), MainActivity.class);
				        startActivity(next);
				        finish();
				    }
		});
		
		success.show();
		_rotate(b_img);
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