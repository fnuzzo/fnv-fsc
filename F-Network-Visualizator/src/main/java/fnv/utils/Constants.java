package fnv.utils;

import java.awt.event.KeyEvent;

public final class Constants {
	/* tag XML del file di input */
	public final static String XML_ROOT = "network";
	public final static String XML_STATIC = "static-data";
	public final static String XML_DYNAMIC = "dynamic-data";
	public final static String XML_NETWORK_NAME = "network-name";
	public final static String XML_NODES_LIST = "nodes-list";
	public final static String XML_NODE = "node";
	public final static String XML_NODE_ID_ATTR = "id";
	public final static String XML_NODE_LABEL_ATTR = "label";
	public final static String XML_NODE_X_ATTR = "x";
	public final static String XML_NODE_Y_ATTR = "y";
	public final static String XML_NODE_Z_ATTR = "z";
	public final static String XML_FLAT = "flat";
	public final static String XML_INSTANT = "instant";
	public final static String XML_INSTANT_VALUE_ATTR = "value";
	public final static String XML_INSTANT_LABEL_ATTR = "label";
	public final static String XML_INTERACTION = "interaction";
	public final static String XML_INTERACTION_SOURCE_ATTR = "source";
	public final static String XML_INTERACTION_TARGET_ATTR = "target";
	public final static String XML_INTERACTION_QUANTITY_ATTR = "quantity";
	public final static String XML_INTERACTION_FREQUENCY_ATTR = "frequency";

	//
	public final static int JSLIDER_TICK_SPACING = 10;

	//
	private final static String ICON_PATH = "/img/";
	public final static String ICON_PLAY = ICON_PATH + "play.png";
	public final static String ICON_PAUSE = ICON_PATH + "pause.png";
	public final static String ICON_STOP = ICON_PATH + "stop.png";
	public final static String ICON_PLAY_TOOLTIP = "Play";
	public final static String ICON_PAUSE_TOOLTIP = "Pause";
	public final static String ICON_STOP_TOOLTIP = "Stop";

	//
	public final static String BUTTON_FILE_LABEL = "File";
	public final static String BUTTON_VIEW_LABEL = "Visualizza";
	public final static String BUTTON_ABOUT_LABEL = "About";
	public final static String BUTTON_AUTHORS_LABEL = "Autori";
	public final static String BUTTON_HELP_LABEL = "Shortcuts";
	public final static String BUTTON_IMPORT_LABEL = "Importa una rete";
	public final static String BUTTON_CREATE_LABEL = "Crea una nuova rete";
	public final static String BUTTON_EXIT_LABEL = "Esci";
	public final static String BUTTON_STRUCTURE_LABEL = "Visualizza spazio 3D di riferimento (s)";
	public final static String BUTTON_EDGEIN_LABEL = "Mostra solo archi entranti (e)";
	public final static String BUTTON_ALLEDGES_LABEL = "Mostra gli archi di tutti i nodi (r)";
	public final static String BUTTON_VISUALIZE_LABEL = "Visualizza tutte le labels";

	//
	public final static String BUTTON_FILE_ACTIONCOMMAND = "file";
	public final static String BUTTON_VIEW_ACTIONCOMMAND = "view";
	public final static String BUTTON_ABOUT_ACTIONCOMMAND = "about";
	public final static String BUTTON_AUTHORS_ACTIONCOMMAND = "authors";
	public final static String BUTTON_HELP_ACTIONCOMMAND = "help";
	public final static String BUTTON_IMPORT_ACTIONCOMMAND = "import";
	public final static String BUTTON_CREATE_ACTIONCOMMAND = "create";
	public final static String BUTTON_EXIT_ACTIONCOMMAND = "exit";
	public final static String ICON_PLAY_ACTIONCOMMAND = "play";
	public final static String ICON_PAUSE_ACTIONCOMMAND = "pause";
	public final static String ICON_STOP_ACTIONCOMMAND = "stop";
	public final static String BUTTON_STRUCTURE_ACTIONCOMMAND = "structure";
	public final static String BUTTON_EDGEIN_ACTIONCOMMAND = "edgeIn";
	public final static String BUTTON_ALLEDGES_ACTIONCOMMAND = "edgeOut";
	public final static String BUTTON_LABEL_ACTIONCOMMAND = "label";

	//
	public final static String FILE_CHOOSER_ROOT = "./";
	public final static String FILE_CHOOSER_ERR_MSG = "File di input mal formattato.";
	public final static String FILE_CHOOSER_FILTER_DESCRIPTION = "xml files";
	public final static String FILE_CHOOSER_FILTER_EXT_LOWER = "xml";
	public final static String FILE_CHOOSER_FILTER_EXT_UPPER = "XML";

	//
	public final static int FOOTER_HEIGHT = 200;
	public final static int LOG_HEIGHT = 150;
	//
	public final static String GUI_ABOUT_MESSAGE = "3D Network Visualizator\n"
		+ "Autori:\n"
		+ "Giacomo Benvenuti\n"
		+ "Enrico D'Angelo\n"
		+ "Fabrizio Nuzzo\n";
		
	public final static String GUI_HELP_MESSAGE = "3D Network Visualizator Shortcuts\n"
		+" tramite i tasti: \n"
		+"- PAGE_UP PAGE_DOWN incrementa e decrementa il tempo dell'animazione\n"
		+"- Ctrl + cursore sul nodo scelto = seleziona un nodo per visualizzare il nome\n"
		+"- Shift + cursore sul nodo scelto =  deseleziona un nodo precedentemente selezionato\n"
		+"- CMD + Trascinamento dell'oggetto col mouse sposta l'oggetto dell'interazione\n"
		+"- tramite i tasti direzionali e' possibile cambiare punto di vista sull'animazione\n"
		+"- 'e' + nodo selezionato visualizza solo le connessioni in entrata del nodo\n " 
		+"- 'r' +nodo selezionato visualizza solo le connessioni del nodo selezionato\n"
		+"- 's' nasconde la struttura";
	
	

	
	
	
}
