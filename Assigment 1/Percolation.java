import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private WeightedQuickUnionUF percolation;
    private WeightedQuickUnionUF fullPercolation;
    private boolean[] connected;
    private int openSites;
    private int virtualNodesSize = 2;
    private int percolationCubeSize;
    private int topVirtualSiteIndex;
    private int bottomVirtualSiteIndex;
    private int percolationNodesSize;

    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException();
        percolationNodesSize = n * n + virtualNodesSize;
        percolation = new WeightedQuickUnionUF(percolationNodesSize);
        fullPercolation = new WeightedQuickUnionUF(percolationNodesSize - 1);
        connected = new boolean[percolationNodesSize];
        topVirtualSiteIndex = percolationNodesSize - 2;
        bottomVirtualSiteIndex = percolationNodesSize - 1;
        openSites = 0;
        percolationCubeSize = n;
    }

    public void open(int row, int col) {
        validateIndexes(row, col);
        int index = convert2DindexTo1D(row, col);
        int indexRow = convert2DindexTo1D(row - 1, col);

        incrementNumberOfSites(row, col);

        connected[index] = Boolean.TRUE;

        if (row == 1) {
            percolation.union(topVirtualSiteIndex, index);
            fullPercolation.union(topVirtualSiteIndex, index);
        }

        if (row == percolationCubeSize) {
            percolation.union(bottomVirtualSiteIndex, index);
        }

        // connect site for row - 1
        if (row - 1 > 0 && connected[indexRow]) {
            percolation.union(index, indexRow);
            fullPercolation.union(index, indexRow);
        }
        // connect site for row + 1
        indexRow = convert2DindexTo1D(row + 1, col);
        if (row + 1 <= percolationCubeSize && connected[indexRow]) {
            percolation.union(index, indexRow);
            fullPercolation.union(index, indexRow);
        }
        // connect site for col - 1
        if (col - 1 > 0 && connected[index - 1]) {
            percolation.union(index, index - 1);
            fullPercolation.union(index, index - 1);
        }
        // connect site for col + 1
        if (col + 1 <= percolationCubeSize && connected[index + 1]) {
            percolation.union(index, index + 1);
            fullPercolation.union(index, index + 1);
        }
    }

    public boolean isOpen(int row, int col) {
        validateIndexes(row, col);
        int index = convert2DindexTo1D(row, col);
        return connected[index] ? Boolean.TRUE : Boolean.FALSE;
    }

    public boolean isFull(int row, int col) {
        validateIndexes(row, col);
        return fullPercolation.connected(convert2DindexTo1D(row, col), topVirtualSiteIndex) && isOpen(row, col);
    }

    public int numberOfOpenSites() {
        return openSites;
    }

    public boolean percolates() {
        return percolation.connected(topVirtualSiteIndex, bottomVirtualSiteIndex);
    }

    private int convert2DindexTo1D(int row, int col) {
        return ((row - 1) * percolationCubeSize) + (col - 1);
    }

    private void validateIndexes(int row, int col) {
        if (row <= 0 || row > percolationCubeSize) {
            throw new IndexOutOfBoundsException("row index i out of bounds");
        }

        if (col <= 0 || col > percolationCubeSize) {
            throw new IndexOutOfBoundsException("row index j out of bounds");
        }
    }

    private void incrementNumberOfSites(int row, int col) {
        if (!isOpen(row, col)) {
            ++openSites;
        }
    }

    public static void main(String[] args) {

    }

}