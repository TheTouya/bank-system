import random
def viza_generator():
    viza = [4, ]
    twos = []
    ones = []
    while True:
        viza.append(random.choice([1,2,3,4,5,6,7,8,9,0]))
        if len(viza) == 16:
            for y in range(0, 15, 2):
                number = int(viza[y]) * 2
                if number > 9:
                    twos.append(number - 9)
                else:
                    twos.append(number)
            for x in range(1, 16, 2):
                ones.append(int(viza[x]) * 1)
            if (sum(twos) + sum(ones)) % 10 == 0:
                answer = [str(x) for x in viza]
                print("".join(answer))
                break
            else:
                viza.clear()
                twos.clear()
                ones.clear()
                viza.append(4)


def master_generator():
    master = [5, ]
    twos = []
    ones = []
    while True:
        master.append(random.choice([1, 2, 3, 4, 5, 6, 7, 8,9,0]))
        if len(master) == 16:
            for y in range(0, 15, 2):
                number = int(master[y]) * 2
                if number > 9:
                    twos.append(number - 9)
                else:
                    twos.append(number)
            for x in range(1, 16, 2):
                ones.append(int(master[x]) * 1)
            if (sum(twos) + sum(ones)) % 10 == 0:
                answer = [str(x) for x in master]
                print("".join(answer))
                break
            else:
                master.clear()
                twos.clear()
                ones.clear()
                master.append(5)


def american_express():
    american = [3,4,]
    ones = []
    twos = []
    while True:
        american.append(random.choice([1,2,3,4,5,6,7,8,9,0]))
        if len(american) == 15:
            american.append(0)
            for x in range(0,15,2):
                number = american[x] * 2
                if number > 9:
                    twos.append(number - 9)
                else:
                    twos.append(x)
            for x in range(1,16,2):
                ones.append(american[x]*1)
            if (sum(twos) + sum(ones)) % 10 == 0:
                american.pop(-1)
                answer = [str(x) for x in american]
                print("".join(answer))
                break
            else:
                american.clear()
                twos.clear()
                ones.clear()
                american.append(3)
                american.append(4)