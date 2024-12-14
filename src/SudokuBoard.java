import java.util.*;

public class SudokuBoard {
  private final PriorityQueue<SudokuTile> board;

  public SudokuBoard() {
    board = getBlankBoard();
  }

  public void solve() {
    int move = 1;
    while (board.peek().getCandidateAmount() != 10) {
      System.out.println("Making move #" + move);
      makeMove();
      move++;
    }
  }

  public int[] makeMove() {
    SudokuTile bestTile = board.peek();
    System.out.println(bestTile);
    if (bestTile.getCandidateAmount() != 1) {
      System.out.println("Trying to change tile with candidate amount " + bestTile.getCandidateAmount());
    }

    int newVal = bestTile.getRandomCandidate();
    boolean wasTileChanged = tryChangeTile(bestTile, newVal);

    if (!wasTileChanged) {
      System.out.println("Changing tile " + bestTile + " to " + newVal + " failed");
    }

    return new int[] {bestTile.getX(), bestTile.getY(), newVal};
  }

  public void tryChangeTile(int x, int y, int newVal) {
    for (SudokuTile tile : board) {
        if (tile.getX() == x && tile.getY() == y) {
          tryChangeTile(tile, newVal);
          return;
        }
    }

  }

  public boolean tryChangeTile(SudokuTile tileToChange, int newVal) {
    if (tileToChange.getValue() != 0) {
      return false;
    }
    ArrayList<SudokuTile> alignedTiles = getAlignedTiles(tileToChange);

    for (SudokuTile tile : alignedTiles) {
      if (tile != tileToChange && tile.getValue() == newVal) {
        return false;
      }
    }

    board.removeAll(alignedTiles);

    for (SudokuTile tile : alignedTiles) {
      tile.getCandidates().remove(newVal);
    }
    tileToChange.setValue(newVal);

    board.addAll(alignedTiles);

    return true;
  }

  public ArrayList<SudokuTile> getAlignedTiles(SudokuTile tile) {
    ArrayList<SudokuTile> result = new ArrayList<>();

    for (SudokuTile tileToCheck : board) {
      if (doTilesAlign(tile.getX(), tile.getY(), tileToCheck.getX(), tileToCheck.getY())) {
        result.add(tileToCheck);
      }
    }

    return result;
  }

  public static boolean doTilesAlign(int x1, int y1, int x2, int y2) {
    // Check if the tiles are in the same square
    if ((x1 - 1) / 3 == (x2 - 1) / 3 && (y1 - 1) / 3 == (y2 - 1) / 3) {
      return true;
    }

    return x1 == x2 || y1 == y2;
  }

  public void randomizeBoard(int numAmount) {
    SudokuTile[] tiles = board.toArray(new SudokuTile[0]);

    for (int index = 0; index < numAmount; index++) {
      int currTileIndex = (int) (Math.random() * tiles.length);

      while (tiles[currTileIndex].getValue() != 0) {
        currTileIndex = (int) (Math.random() * tiles.length);
      }

      int newVal = tiles[currTileIndex].getRandomCandidate();
      boolean wasTileChanged = tryChangeTile(tiles[currTileIndex], newVal);

      while (!wasTileChanged) {
        newVal = tiles[currTileIndex].getRandomCandidate();
        wasTileChanged = tryChangeTile(tiles[currTileIndex], newVal);
      }
    }
    board.clear();
    board.addAll(List.of(tiles));
  }

  public static PriorityQueue<SudokuTile> getBlankBoard() {
    PriorityQueue<SudokuTile> result =
        new PriorityQueue<>(Comparator.comparingInt(SudokuTile::getCandidateAmount));

    for (int y = 1; y <= 9; y++) {
      for (int x = 1; x <= 9; x++) {
        result.add(new SudokuTile(x, y));
      }
    }

    return result;
  }

  public PriorityQueue<SudokuTile> getBoard() {
    return board;
  }

  public int[][] getIntBoard() {
    int[][] result = new int[9][];

    for (int index = 0; index < 9; index++) {
      result[index] = new int[9];
    }

    for (SudokuTile tile : board) {
      result[tile.getY() - 1][tile.getX() - 1] = tile.getValue();
    }

    return result;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("-".repeat(25)).append('\n');
    int[][] intBoard = getIntBoard();

    for (int y = 0; y < 9; y++) {
      for (int x = 0; x < 9; x++) {
        if (x % 3 == 0) {
          sb.append("| ");
        }

        if (intBoard[y][x] == 0) {
          sb.append("  ");
        } else {
          sb.append(intBoard[y][x]).append(' ');
        }
      }
      sb.append("|\n");

      if ((y + 1) % 3 == 0) {
        sb.append("-".repeat(25)).append('\n');
      }
    }

    return sb.toString();
  }
}
