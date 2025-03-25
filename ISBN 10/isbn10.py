import random
def generate_isbn10():
    digits = [random.randint(0, 9) for _ in range(9)]
    checksum = sum((i + 1) * digits[i] for i in range(9)) % 11
    digits.append('X' if checksum == 10 else checksum)
    print(''.join(map(str, digits)))