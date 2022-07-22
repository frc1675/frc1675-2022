package frc.robot.utils;

import static edu.wpi.first.wpilibj.util.ErrorMessages.requireNonNullParam;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

import edu.wpi.first.networktables.NTSendable;
import edu.wpi.first.networktables.NTSendableBuilder;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.DriverStation;


/** The below class was copied almost entirely from SendableChooser.class. Since Sendable chooser has so many private attributes, it cannot be extended easily.  */
public class EditableChooser<V> implements NTSendable, AutoCloseable{
    /** The key for the default value. */
  private static final String DEFAULT = "default";
  /** The key for the selected option. */
  private static final String SELECTED = "selected";
  /** The key for the active option. */
  private static final String ACTIVE = "active";
  /** The key for the option array. */
  private static final String OPTIONS = "options";
  /** The key for the instance number. */
  private static final String INSTANCE = ".instance";
  /** A map linking strings to the objects the represent. */
  private Map<String, V> m_map = new LinkedHashMap<>();

  private String m_defaultChoice = "";
  private final int m_instance;
  private static final AtomicInteger s_instances = new AtomicInteger();

  public EditableChooser() {
    m_instance = s_instances.getAndIncrement();
    SendableRegistry.add(this, "SendableChooser", m_instance);
  }

//my methods

public void clearExceptDefault(){
        V temp = m_map.get(m_defaultChoice);
        m_map.clear();
        m_map.put(m_defaultChoice, temp);
        m_selected = m_defaultChoice;
}

public void removeOption(String name){
    if(m_map.containsKey(name)){
        m_map.remove(name);
    }else{
        DriverStation.reportError("Error: Key '"+name+"' does not exist (EditableChooser.removeOptions).", false);
    }
}

//copied methods
  @Override
  public void close() {
    SendableRegistry.remove(this);
  }

  public void addOption(String name, V object) {
    m_map.put(name, object);
  }

  public void setDefaultOption(String name, V object) {
    requireNonNullParam(name, "name", "setDefaultOption");

    m_defaultChoice = name;
    addOption(name, object);
  }

  public V getSelected() {
    m_mutex.lock();
    try {
      if (m_selected != null) {
        return m_map.get(m_selected);
      } else {
        return m_map.get(m_defaultChoice);
      }
    } finally {
      m_mutex.unlock();
    }
  }

  private String m_selected;
  private final List<NetworkTableEntry> m_activeEntries = new ArrayList<>();
  private final ReentrantLock m_mutex = new ReentrantLock();

  @Override
  public void initSendable(NTSendableBuilder builder) {
    builder.setSmartDashboardType("String Chooser");
    builder.getEntry(INSTANCE).setDouble(m_instance);
    builder.addStringProperty(DEFAULT, () -> m_defaultChoice, null);
    builder.addStringArrayProperty(OPTIONS, () -> m_map.keySet().toArray(new String[0]), null);
    builder.addStringProperty(
        ACTIVE,
        () -> {
          m_mutex.lock();
          try {
            if (m_selected != null) {
              return m_selected;
            } else {
              return m_defaultChoice;
            }
          } finally {
            m_mutex.unlock();
          }
        },
        null);
    m_mutex.lock();
    try {
      m_activeEntries.add(builder.getEntry(ACTIVE));
    } finally {
      m_mutex.unlock();
    }
    builder.addStringProperty(
        SELECTED,
        null,
        val -> {
          m_mutex.lock();
          try {
            m_selected = val;
            for (NetworkTableEntry entry : m_activeEntries) {
              entry.setString(val);
            }
          } finally {
            m_mutex.unlock();
          }
        });
  }
}
