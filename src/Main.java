public class Main {
  public static void main(String[] args) throws InterruptedException {
    /*SudokuBoard board = new SudokuBoard();
    testBoard1(board);
    board.solveRecursively();

    System.out.println(board);*/

    WebSudokuPlayer player = new WebSudokuPlayer(4);
    player.initializeBoard();
    SudokuBoard board = new SudokuBoard(player.getBoard());

    board.solveRecursively();
    System.out.println(board);


    // player.solve();
  }

  public static void testBoard1(SudokuBoard board) {
    board.tryChangeTile(1, 1, 4);
    board.tryChangeTile(3, 2, 1);
    board.tryChangeTile(4, 1, 5);
    board.tryChangeTile(6, 2, 2);
    board.tryChangeTile(6, 3, 7);
    board.tryChangeTile(7, 1, 7);
    board.tryChangeTile(8, 2, 8);
    board.tryChangeTile(7, 3, 9);
    board.tryChangeTile(2, 4, 3);
    board.tryChangeTile(3, 4, 6);
    board.tryChangeTile(2, 6, 8);
    board.tryChangeTile(5, 4, 4);
    board.tryChangeTile(4, 5, 2);
    board.tryChangeTile(5, 6, 3);
    board.tryChangeTile(9, 4, 2);
    board.tryChangeTile(9, 6, 6);
    board.tryChangeTile(1, 8, 1);
    board.tryChangeTile(1, 9, 3);
    board.tryChangeTile(4, 7, 9);
    board.tryChangeTile(4, 9, 6);
    board.tryChangeTile(6, 7, 8);
    board.tryChangeTile(6, 8, 5);
    board.tryChangeTile(4, 9, 6);
    board.tryChangeTile(7, 7, 5);
    board.tryChangeTile(7, 8, 8);
    board.tryChangeTile(8, 9, 1);
  }
}
