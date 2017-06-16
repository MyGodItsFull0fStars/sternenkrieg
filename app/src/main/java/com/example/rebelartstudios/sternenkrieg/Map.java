package com.example.rebelartstudios.sternenkrieg;

import android.content.ClipData;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.rebelartstudios.sternenkrieg.GameLogic.PlayerFieldPositionString;
import com.example.rebelartstudios.sternenkrieg.GameLogic.ShipLogic;
import com.example.rebelartstudios.sternenkrieg.Network.AcceptThread;
import com.example.rebelartstudios.sternenkrieg.Network.NetworkUtilities;
import com.example.rebelartstudios.sternenkrieg.Network.ReceiveThreadClient;
import com.example.rebelartstudios.sternenkrieg.Network.ReceiveThreadHost;
import com.example.rebelartstudios.sternenkrieg.Network.StartThread;

import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Map Class gets started after clicking the Main class.
 * In this class, the player sets the position of the ships using drag and drop.
 */
public class Map extends AppCompatActivity {
	GridView gridView;
	ImageView imageView;
	ImageView ship1, ship2, ship3, turn, play;
	String playerField[] = new String[64];
	int width;
	int height;
	int oldpos;
	int degree = 0;
	int which_ship;
	boolean smallShipIsSetOnField, middleShipIsSetOnField, bigShipIsSetOnField = false;
	String setPlayerPositionD = "d";
	String setPlayerPositionE = "e";
	String setPlayerPositionF = "f";
	String setPlayerPositionZERO = "0";
	MapLoad mapLoad;
	int value;

	Socket socket = new Socket();
	ServerSocket mServerSocket = null;
	Handler myhandler;
	boolean Phost = false; // if this is host then Phost is true; if not is false.
	String message;
	ReceiveThreadHost receiveThreadHost;
	String ip;
	ReceiveThreadClient receiveThreadClient;
	String tag = "Map";
	AcceptThread mAcceptThread;
	StartThread startThread;
	OutputStream os = null;
	boolean Net = false;
	int i = 1;
	Intent intent = new Intent();
	boolean finish = false;
	boolean finishEnemy = false;
	NetworkUtilities util;
	NetworkStats stats = new NetworkStats();
	ShipLogic shipLogic = new ShipLogic();
	PlayerFieldPositionString fieldStrings = new PlayerFieldPositionString();

	public void initializeMap() {
		mapLoad = new MapLoad(this, playerField);
	}


	/**
	 * Returns the player field as a String array
	 *
	 * @return
	 */
	public String[] getPlayerField() {
		// playerField[23] = setPlayerPositionTWO;
		return playerField;
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.activity_map);
		initializeImageViews();
		initializePlayerField();
		initializeMap();

		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();

		//werden für die größer der Map benötigt
		display.getSize(size);
		width = size.x;
		height = size.y;

		initializeShipView();

		/********************Netz**************************/
		System.out.println("Map");
		Phost = stats.isPhost();
		System.out.println("Phost: " + Phost);
		Net = stats.isNet();
		System.out.println("Net: " + Net);
		if (Phost == false) {
			ip = stats.getIp();
			System.out.println("Ip: " + ip);
		}


		myhandler = new Myhandler();
		util = new NetworkUtilities(
				Phost,
				mAcceptThread,
				mServerSocket,
				socket,
				myhandler,
				receiveThreadHost,
				startThread,
				ip,
				receiveThreadClient);
		util.networkbuild();


		util.connection();


		/********************Netz**************************/


		//Glide.with(this).load(R.raw.fog).asGif().into(((ImageView)gridView));
		initializeOnClickListenerOnButton();
		//ClickListener gibt die Position #Kachel zurück
		gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

				Toast.makeText(getApplicationContext(), "Pos: " + position + " Id: ",
						Toast.LENGTH_SHORT).show();
			   /* playerField[position] = 1 + "";
                draw(playerField);*/


			}
		});
		gridView.setOnDragListener(new AdapterView.OnDragListener() {
			@Override
			public boolean onDrag(View v, DragEvent event) {
				final int action = event.getAction();
				switch (action) {
					case DragEvent.ACTION_DRAG_STARTED:
						break;

					case DragEvent.ACTION_DRAG_EXITED:
						break;

					case DragEvent.ACTION_DRAG_ENTERED:

						break;

					case DragEvent.ACTION_DROP: {
						int x = (int) event.getX();
						int y = (int) event.getY();
						int pos = position(x, y);

						//kleines Schiff
						if (which_ship == shipLogic.SMALL_SHIP) {
							if (!playerField[pos].equals(setPlayerPositionE) && !playerField[pos].equals(setPlayerPositionF)) {
								//falls schon mal gesetzt wird die letzte Position gelöscht
								delete(shipLogic.getSmallShipArray());
								//neue Position gesetzt
								playerField[pos] = setPlayerPositionD;
								shipLogic.setSmallShipPosition(pos);
							}
							smallShipIsSetOnField = true;
						}

						//mittleres Schiff
						if (which_ship == shipLogic.MIDDLE_SHIP) {
							if (degree == fieldStrings.HORIZONTAL) {
								//check_position schaut dass das schiff nicht außerhalb der playerField oder vom rechten ende der playerField auf die linke seite gesetzt wird
								if (check_position(pos, which_ship, degree)) {
									delete(shipLogic.getMiddleShipArray());
									// pos-1 weil wenn man das Bild bewegt ist der Zeiger genau mittig vom Bild
									playerField[pos - 1] = setPlayerPositionE;
									playerField[pos] = setPlayerPositionE;
									shipLogic.setMiddleShipPosition(pos, degree);
								}
							} else if (degree == fieldStrings.VERTICAL) {
								if (check_position(pos, which_ship, degree)) {
									delete(shipLogic.getMiddleShipArray());
									playerField[pos - 8] = setPlayerPositionE;
									playerField[pos] = setPlayerPositionE;
									shipLogic.setMiddleShipPosition(pos, degree);
								}

							}
							middleShipIsSetOnField = true;

						}
						//großes Schiff
						if (which_ship == shipLogic.BIG_SHIP) {
							if (degree == fieldStrings.HORIZONTAL) {
								if (check_position(pos, which_ship, degree)) {
									delete(shipLogic.getBigShipArray());
									// pos-1 weil wenn man das Bild bewegt ist der Zeiger genau mittig vom Bild
									playerField[pos - 1] = setPlayerPositionF;
									playerField[pos] = setPlayerPositionF;
									playerField[pos + 1] = setPlayerPositionF;
									shipLogic.setBigShipPosition(pos, degree);
								}
							} else if (degree == fieldStrings.VERTICAL) {
								if (check_position(pos, which_ship, degree)) {
									delete(shipLogic.getBigShipArray());
									playerField[pos - 8] = setPlayerPositionF;
									playerField[pos] = setPlayerPositionF;
									playerField[pos + 8] = setPlayerPositionF;
									shipLogic.setBigShipPosition(pos, degree);
								}
							}
							bigShipIsSetOnField = true;

						}

						draw(playerField);
						setShipsVisible();


						if (smallShipIsSetOnField && middleShipIsSetOnField && bigShipIsSetOnField) {
							play.setImageDrawable(getResources().getDrawable(R.drawable.arrow_right_other));
						}

						return (true);

					}

					case DragEvent.ACTION_DRAG_ENDED: {
						return (true);

					}
					default:
						break;
				}
				return true;
			}

		});
		//ShadowBuilder erzeugt eine Animation, wenn man das schiff los lässt
		ship1.setOnTouchListener(new View.OnTouchListener()

		{

			@Override
			public boolean onTouch(View v, MotionEvent arg1) {
				ClipData data = ClipData.newPlainText("", "");
				View.DragShadowBuilder shadow = new View.DragShadowBuilder(ship1);
				v.startDrag(data, shadow, null, 0);
				//small ship
				which_ship = shipLogic.SMALL_SHIP;
				ship1.setVisibility(View.INVISIBLE);
				return false;
			}
		});
		ship2.setOnTouchListener(new View.OnTouchListener()

		{

			@Override
			public boolean onTouch(View v, MotionEvent arg1) {
				ClipData data = ClipData.newPlainText("", "");
				View.DragShadowBuilder shadow = new View.DragShadowBuilder(ship2);
				v.startDrag(data, shadow, null, 0);
				//middle ship
				which_ship = shipLogic.MIDDLE_SHIP;
				ship2.setVisibility(View.INVISIBLE);
				return false;
			}
		});
		ship3.setOnTouchListener(new View.OnTouchListener()

		{

			@Override
			public boolean onTouch(View v, MotionEvent arg1) {
				ClipData data = ClipData.newPlainText("", "");
				View.DragShadowBuilder shadow = new View.DragShadowBuilder(ship3);

				v.startDrag(data, shadow, null, 0);
				//big ship
				which_ship = shipLogic.BIG_SHIP;
				ship3.setVisibility(View.INVISIBLE);
				return false;
			}
		});


	}

	private void setShipsVisible() {
		ship1.setVisibility(View.VISIBLE);
		ship2.setVisibility(View.VISIBLE);
		ship3.setVisibility(View.VISIBLE);
	}

	//schaut das das Schiff nicht über die Map hinaus gesetzt wird
	public boolean check_position(int pos, int size, int degree) {
		ArrayList<Integer> failures_right = new ArrayList<Integer>(Arrays.asList(7, 15, 23, 31, 39, 47, 55, 63));
		ArrayList<Integer> failures_left = new ArrayList<Integer>(Arrays.asList(8, 16, 24, 32, 40, 48, 56));

		String[] length = new String[size];
		//linkes und rechtes Ende der Map
		if (degree == 180 || degree == 0) {
			if (size == 1) {
				if (failures_right.contains(pos - 1) || failures_left.contains(pos) || pos < 1 || pos > 62) {
					return false;
				}
			} else if (size == 2) {
				if (failures_right.contains(pos - 1) || failures_right.contains(pos) || failures_left.contains(pos) || pos < 1 || pos > 62) {
					return false;
				}
			}
		}
		//Oben und Unten
		// TODO: 15/06/2017 can't be reached at the moment
		if (degree == 90 || degree == 270) {
			if (size == 1) {
				if (pos < 8 || pos > 63) {
					return false;
				}
			} else if (size == 2) {
				if (pos < 8 || pos > 55) {
					return false;
				}
			}
		}

		if (which_ship == shipLogic.BIG_SHIP) {
			if (degree == fieldStrings.HORIZONTAL) {
				if (playerField[pos - 1].equals(setPlayerPositionD) || playerField[pos].equals(setPlayerPositionD) || playerField[pos + 1].equals(setPlayerPositionD)
						|| playerField[pos - 1].equals(setPlayerPositionE) || playerField[pos].equals(setPlayerPositionE) || playerField[pos + 1].equals(setPlayerPositionE)) {
					return false;
				}
			} else {
				if (playerField[pos - 8].equals(setPlayerPositionD) || playerField[pos].equals(setPlayerPositionD) || playerField[pos + 8].equals(setPlayerPositionD)
						|| playerField[pos - 8].equals(setPlayerPositionE) || playerField[pos].equals(setPlayerPositionE) || playerField[pos + 8].equals(setPlayerPositionE)) {
					return false;
				}
			}
		} else if (which_ship == shipLogic.MIDDLE_SHIP) {
			if (degree == fieldStrings.HORIZONTAL) {
				if (playerField[pos - 1].equals(setPlayerPositionD) || playerField[pos].equals(setPlayerPositionD)
						|| playerField[pos - 1].equals(setPlayerPositionF) || playerField[pos].equals(setPlayerPositionF)) {
					return false;
				}
			} else {
				if (playerField[pos - 8].equals(setPlayerPositionD) || playerField[pos].equals(setPlayerPositionD)
						|| playerField[pos - 8].equals(setPlayerPositionF) || playerField[pos].equals(setPlayerPositionF)) {
					return false;
				}
			}
		}
		return true;
	}


	/**
	 * Initializes the ImageViews in the maps activity class when starting the
	 * onCreate() method
	 */
	private void initializeImageViews() {
		imageView = (ImageView) findViewById(R.id.grid_item_image);
		ship1 = (ImageView) findViewById(R.id.image_ship1);
		ship2 = (ImageView) findViewById(R.id.image_ship2);
		ship3 = (ImageView) findViewById(R.id.image_ship3);
		turn = (ImageView) findViewById(R.id.image_turn);
		play = (ImageView) findViewById(R.id.play);
	}


	/**
	 * Initializes the player field in the onCreate() method
	 * player field starts as an "empty" array, containing only zeroes.
	 */
	private void initializePlayerField() {
		playerField = new String[64];
		for (int i = 0; i < 64; i++) {
			playerField[i] = setPlayerPositionZERO;
		}
	}

	/**
	 * Initializes the ShipViews in the onCreate() method
	 */
	private void initializeShipView() {
		gridView = (GridView) findViewById(R.id.gridView);
		gridView.getLayoutParams().height = height - 350;
		gridView.getLayoutParams().width = height - 350;
		ship1.getLayoutParams().height = (height - 350) / 8;
		ship1.getLayoutParams().width = (height - 350) / 8;
		ship2.getLayoutParams().height = (height - 350) / 8;
		ship2.getLayoutParams().width = (height - 350) / 4;
		ship3.getLayoutParams().height = (height - 350) / 8;
		ship3.getLayoutParams().width = (height - 350) / 3;
	}

	/**
	 * Initializes the onClickListeners for the buttons, gridview
	 * in the maps activity class
	 */
	private void initializeOnClickListenerOnButton() {

		play.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				if (smallShipIsSetOnField && middleShipIsSetOnField && bigShipIsSetOnField) {

					intent.setClass(Map.this, Spielfeld.class);
					NetworkStats.setPlayerMap(playerField);
					finish = true;
					util.messageSend("boolean", Phost, true);
					if (!Phost) {
						new CountDownTimer(500, 100) {
							public void onTick(long millisUntilFinished) {
								System.out.print(millisUntilFinished);
							}

							@Override
							public void onFinish() {
								syncClose();
							}

						}.start();
					} else
						syncClose();

				}

			}
		});

		gridView.setAdapter(new MapLoad(this, playerField));

        /*
        When this button gets clicked, the ships rotate around the Y axis
         */
		turn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (degree == fieldStrings.HORIZONTAL) {
					degree = fieldStrings.VERTICAL;
					ship1.animate().rotationBy(270).start();
					ship2.animate().rotationBy(270).start();
					ship3.animate().rotationBy(270).start();
				} else {
					degree = fieldStrings.HORIZONTAL;
					ship1.animate().rotationBy(90).start();
					ship2.animate().rotationBy(90).start();
					ship3.animate().rotationBy(90).start();
				}
			}
		});
	}

	public int position(int x, int y) {
		int zehner = y * 8 / (height - 350);
		zehner = zehner * 8;
		int einer = x * 8 / (height - 350);
		int pos = zehner + einer;
		return pos;
	}

	/**
	 * Receives an integer array containing the positions, which will be deleted
	 * in the player field
	 *
	 * @param data
	 */
	public void delete(int data[]) {
		if (data != null) {
			for (int x : data) {
				playerField[x] = setPlayerPositionZERO;
			}

		}
		draw(playerField);

	}

	public void draw(String[] array) {
		gridView.setAdapter(mapLoad);
	}


	public void syncClose() {
		if (finish && finishEnemy) {
			util.close();
			startActivity(intent);

		}
	}


	/********************Netz**************************/


	// There are the Message from other player. We can work with "message" to change our map, uppower and ship.
	class Myhandler extends Handler {


		public void handleMessage(Message msg) {


			switch (msg.what) {
				case 1:
					message = (String) msg.obj;
					message = (String) msg.obj;
					int count = 0;
					if (message == null) {
						count++;
					} else {
						count = 0;
					}
					if (count == 3) {
						util.close();
					}
					if (!(message == null)) {
						if (message.equals("boolean")) {
							finishEnemy = true;
							syncClose();

						}
					}

					System.out.println("Message: " + message);

					break;
				case 0:
					displayToast("Erfolg");
					break;
				case 2:
					displayToast("!");
					break;
			}
		}

	}

	private void displayToast(String s) {
		Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
	}


}

