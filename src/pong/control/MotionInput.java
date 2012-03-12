package pong.control;
import java.io.InputStream;
import java.io.OutputStream;
import gnu.io.CommPortIdentifier; 
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent; 
import gnu.io.SerialPortEventListener; 
import java.util.Enumeration;

public class MotionInput implements SerialPortEventListener {
	SerialPort serialPort;
        /** The port we're normally going to use. */
	private static final String PORT_NAMES[] = { 
			"/dev/tty.usbserial-A9007UX1", // Mac OS X
			"/dev/ttyUSB0", // Linux
			"COM4", // Windows
			};
	/** Buffered input stream from the port */
	private InputStream input;
	/** The output stream to the port */
	private OutputStream output;
	private String xy;
	//private String[] goodInput = new String[2];
	private String[] goodInput = new String[3];
	private float[] goodInputf = new float[3];
	private float[] f = new float[3];
	/** Milliseconds to block while waiting for port open */
	private static final int TIME_OUT = 2000;
	/** Default bits per second for COM port. */
	private static final int DATA_RATE = 115200;
	
	public MotionInput(){
		this.initialize();
	}

	public void initialize() {
		CommPortIdentifier portId = null;
		Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

		// iterate through, looking for the port
		while (portEnum.hasMoreElements()) {
			CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
			for (String portName : PORT_NAMES) {
				if (currPortId.getName().equals(portName)) {
					portId = currPortId;
					break;
				}
			}
		}

		if (portId == null) {
			System.out.println("Could not find COM port.");
			return;
		}

		try {
			// open serial port, and use class name for the appName.
			serialPort = (SerialPort) portId.open(this.getClass().getName(),
					TIME_OUT);

			// set port parameters
			serialPort.setSerialPortParams(DATA_RATE,
					SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);

			// open the streams
			input = serialPort.getInputStream();
			output = serialPort.getOutputStream();

			// add event listeners
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
		} catch (Exception e) {
			System.err.println(e.toString());
		}
	}

	/**
	 * This should be called when you stop using the port.
	 * This will prevent port locking on platforms like Linux.
	 */
	public synchronized void close() {
		if (serialPort != null) {
			serialPort.removeEventListener();
			serialPort.close();
		}
	}

	/**
	 * Handle an event on the serial port. Read the data and print it.
	 */
	public synchronized void serialEvent(SerialPortEvent oEvent) {
		if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {
				int available = input.available();
				byte chunk[] = new byte[available];
				input.read(chunk, 0, available);
				// Displayed results are codepage dependent
				xy = new String(chunk);
				String[] xyv = xy.split(":");
				//System.out.println(xy);
				//upp/ner först
				if(xyv[0].equals("-")){
					xyv[0] = goodInput[0];
				}else if ( !xyv[0].equals("-")){
					goodInput[0] = xyv[0];
				}else if(xyv[1].equals("-")){
					xyv[1] = goodInput[1];
				}else if(!xyv[1].equals("-")){
					goodInput[1] = xyv[1];
				}
				
				
//				xy.trim();
//		        xy.replaceAll("\\n","");
//		        xy.replaceAll("\\t","");
				// CONVERT to INTEGER
				
		        //int i = Integer.parseInt(xy);
				//Float f = new Float(xy);
				f[0] = Float.parseFloat(xyv[0]);
				System.out.println("FLOAT1 " + f[0]);
				f[1] = Float.parseFloat(xyv[1]);
				System.out.println("FLOAT2 " + f[1]);
				
				if(f[0]<-10 || f[0]>10){
					f[0] = goodInputf[0];
				}else if (f[0]>-10  && f[0]<10){
					goodInputf[0] = f[0];
				}else if(f[1]<-10 || f[1]>10){
					f[1] = goodInputf[1];
				}else if(f[0]>-10  && f[0]<10){
					goodInputf[1] = f[1];
				}

				//System.out.println("FLOAT: "+f);
			} catch (Exception e) {
				System.err.println(e.toString());
			}
		}
		// Ignore all the other eventTypes, but you should consider the other ones.
	}
	public float[] getf(){
		return f;
	}
}