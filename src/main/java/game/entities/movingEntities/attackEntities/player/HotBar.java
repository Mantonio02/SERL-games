package game.entities.movingEntities.attackEntities.player;

import game.items.IItem;
import java.util.Arrays;

public class HotBar {
  private IItem[] items;
  private int selectedItem;

  public HotBar(IItem[] items) {
    this.items = items;
  }

  /**
   * Sets the item at the given index to be selected.
   *
   * @param index of the item to be selected.
   * @throws IndexOutOfBoundsException if the index is outside the range of the HotBar
   */
  protected void setSelectedItem(int index) throws IndexOutOfBoundsException {
    if (index < 0 || index >= this.items.length) {
      throw new IndexOutOfBoundsException();
    }
    this.selectedItem = index;
  }

  /**
   * Sets the item after the currently selected item to be selected. If there is no item after the
   * currently selected item, the first item of the hot bar is selected.
   */
  protected void incrementSelectedItem() {
    this.selectedItem = (this.selectedItem + 1) % this.items.length;
  }

  /**
   * Sets the item before the currently selected item to be selected. If there is no item before the
   * currently selected item, the last item of the hot bar is selected.
   */
  protected void decrementSelectedItem() {
    if (this.selectedItem-- == 0) {
      this.selectedItem = this.items.length - 1;
    }
  }

  /**
   * Returns the {@link IItem} at the given index of the hot bar.
   *
   * @param index of the item to return
   * @return the {@link IItem} at the given index of the hot bar.
   */
  protected IItem getItem(int index) {
    return this.items[index];
  }

  /**
   * @return the index of the item currently selected in the hot bar.
   */
  protected int getSelectedItem() {
    return this.selectedItem;
  }

  /**
   * @return the total number of slots in the hot bar.
   */
  protected int getNumSlots() {
    return this.items.length;
  }

  /**
   * @return the number of items in the hot bar.
   */
  public int getNumItems() {
    int counter = 0;
    for (IItem item : items) {
      if (item != null) {
        counter++;
      }
    }
    return counter;
  }

  /** Removes every item from the hot bar. */
  public void clearItems() {
    Arrays.fill(items, null);
  }
}
