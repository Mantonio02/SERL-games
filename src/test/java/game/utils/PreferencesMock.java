package game.utils;

import com.badlogic.gdx.Preferences;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/** Acts similar to {@link Preferences}, only it saves in a hashmap and not a actual file. */
public class PreferencesMock implements Preferences {
  private Map<String, Object> preference = new HashMap<>();

  @Override
  public Preferences putBoolean(String key, boolean val) {
    preference.put(key, val);
    return this;
  }

  @Override
  public Preferences putInteger(String key, int val) {
    preference.put(key, val);
    return this;
  }

  @Override
  public Preferences putLong(String key, long val) {
    preference.put(key, val);
    return this;
  }

  @Override
  public Preferences putFloat(String key, float val) {
    preference.put(key, val);
    return this;
  }

  @Override
  public Preferences putString(String key, String val) {
    preference.put(key, val);
    return this;
  }

  @Override
  public Preferences put(Map<String, ?> vals) {
    for (Map.Entry<String, ?> entry : vals.entrySet()) {
      preference.put(entry.getKey(), entry.getValue());
    }
    return this;
  }

  @Override
  public boolean getBoolean(String key) {
    Boolean value = (Boolean) preference.get(key);
    return (value != null) ? value : false;
  }

  @Override
  public int getInteger(String key) {
    Integer value = (Integer) preference.get(key);
    return (value != null) ? value : 0;
  }

  @Override
  public long getLong(String key) {
    Long value = (Long) preference.get(key);
    return (value != null) ? value : 0L;
  }

  @Override
  public float getFloat(String key) {
    Float value = (Float) preference.get(key);
    return (value != null) ? value : 0.0f;
  }

  @Override
  public String getString(String key) {
    String value = (String) preference.get(key);
    return (value != null) ? value : "";
  }

  @Override
  public boolean getBoolean(String key, boolean defValue) {
    return (preference.containsKey(key)) ? (boolean) preference.get(key) : defValue;
  }

  @Override
  public int getInteger(String key, int defValue) {
    return (preference.containsKey(key)) ? (int) preference.get(key) : defValue;
  }

  @Override
  public long getLong(String key, long defValue) {
    return (preference.containsKey(key)) ? (long) preference.get(key) : defValue;
  }

  @Override
  public float getFloat(String key, float defValue) {
    return (preference.containsKey(key)) ? (float) preference.get(key) : defValue;
  }

  @Override
  public String getString(String key, String defValue) {
    return (preference.containsKey(key)) ? (String) preference.get(key) : defValue;
  }

  @Override
  public Map<String, ?> get() {
    return Collections.unmodifiableMap(new HashMap<>(preference));
  }

  @Override
  public boolean contains(String key) {
    return preference.containsKey(key);
  }

  @Override
  public void clear() {
    preference.clear();
  }

  @Override
  public void remove(String key) {
    preference.remove(key);
  }

  @Override
  public void flush() {}
}
