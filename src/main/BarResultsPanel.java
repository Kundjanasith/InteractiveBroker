package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

import com.ib.controller.ApiController;
import com.ib.controller.ApiController.IHistoricalDataHandler;
import com.ib.controller.Bar;

import apidemo.Chart;
import apidemo.util.NewTabbedPanel.NewTabPanel;

public class BarResultsPanel extends NewTabPanel implements IHistoricalDataHandler, ApiController.IRealTimeBarHandler {
	final BarModel m_model = new BarModel();
	final ArrayList<Bar> m_rows = new ArrayList<Bar>();
	final boolean m_historical;
	final Chart m_chart = new Chart( m_rows);
	
	BarResultsPanel( boolean historical) {
		m_historical = historical;
		
		JTable tab = new JTable( m_model);
		JScrollPane scroll = new JScrollPane( tab) {
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.width = 500;
				return d;
			}
		};

		JScrollPane chartScroll = new JScrollPane( m_chart);

		setLayout( new BorderLayout() );
		add( scroll, BorderLayout.WEST);
		add( chartScroll, BorderLayout.CENTER);
	}

	/** Called when the tab is first visited. */
	@Override public void activated() {
	}

	/** Called when the tab is closed by clicking the X. */
	@Override public void closed() {
//		if (m_historical) {
//			apiDemo.controller().cancelHistoricalData( this);
//		}
//		else {
//			apiDemo.controller().cancelRealtimeBars( this);
//		}
	}

	@Override public void historicalData(Bar bar, boolean hasGaps) {
		m_rows.add( bar);
	}
	
	@Override public void historicalDataEnd() {
		fire();
	}

	@Override public void realtimeBar(Bar bar) {
		m_rows.add( bar); 
		fire();
	}
	
	private void fire() {
		SwingUtilities.invokeLater( new Runnable() {
			@Override public void run() {
				m_model.fireTableRowsInserted( m_rows.size() - 1, m_rows.size() - 1);
				m_chart.repaint();
			}
		});
	}

	class BarModel extends AbstractTableModel {
		@Override public int getRowCount() {
			return m_rows.size();
		}

		@Override public int getColumnCount() {
			return 7;
		}
		
		@Override public String getColumnName(int col) {
			switch( col) {
				case 0: return "Date/time";
				case 1: return "Open";
				case 2: return "High";
				case 3: return "Low";
				case 4: return "Close";
				case 5: return "Volume";
				case 6: return "WAP";
				default: return null;
			}
		}

		@Override public Object getValueAt(int rowIn, int col) {
			Bar row = m_rows.get( rowIn);
			switch( col) {
				case 0: return row.formattedTime();
				case 1: return row.open();
				case 2: return row.high();
				case 3: return row.low();
				case 4: return row.close();
				case 5: return row.volume();
				case 6: return row.wap();
				default: return null;
			}
		}
	}		
}
