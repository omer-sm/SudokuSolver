import java.util.HashSet;

public class SudokuTile {
  private int x;
  private int y;
  private final HashSet<Integer> candidates;
  private int value;

  public SudokuTile(int x, int y) {
    this.x = x;
    this.y = y;
    this.candidates = getInitialCandidates();
    this.value = 0;
  }

  public SudokuTile(int x, int y, int value) {
    this.x = x;
    this.y = y;
    this.candidates = getInitialCandidates();
    this.value = value;
  }

  public int getRandomCandidate() {
    Integer[] candidatesArr = getCandidates().toArray(new Integer[0]);
    int index = (int) (Math.random() * candidatesArr.length);

    return candidatesArr[index];
  }

  public int getCandidateAmount() {
    if (value != 0) {
      return 10;
    }

    return candidates.size();
  }

  public HashSet<Integer> getCandidates() {
    return candidates;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public void setValue(int newValue) {
    if (value != 0) {
      throw new RuntimeException(
          "Tried to change value to the tile at ("
              + x
              + ", "
              + y
              + ") but it already has a value.");
    }

    value = newValue;
  }

  public int getValue() {
    return value;
  }

  @Override
  public String toString() {
    return "Tile (" + x + ", " + y + "), value: " + value + ". " + getCandidateAmount() + " candidates.";
  }

  public static HashSet<Integer> getInitialCandidates() {
    HashSet<Integer> result = new HashSet<>();

    for (int num = 1; num <= 9; num++) {
      result.add(num);
    }

    return result;
  }
}
