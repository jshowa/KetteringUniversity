public class Ragged2DArraySumTest {
public static void main(String[] args) {

int[][] triangleArray = {
{1, 2, 3, 4, 5},
{2, 3, 4, 5},
{3, 4, 5},
{4, 5},
{5}
};
int sum = 0;

for (int row = 0; row < triangleArray.length; row++) {
	for (int column = 0; column < triangleArray[row].length; column++) {
		 sum += triangleArray[row][column];
	}
}

System.out.print(sum);
}
}