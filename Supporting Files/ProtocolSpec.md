# ArduGlove Protocol Specification

- Serial communication at baudrate 38400
- Packets are sent as strings delimited by newlines
- Values are delimited by spaces
- Reference implementation sends 120 packets per second

## Packet value order

1. Acceleration X, Integer
1. Acceleration Y, Integer
1. Acceleration Z, Integer
1. Gyroscope X, Integer
1. Gyroscope Y, Integer
1. Gyroscope Z, Integer
1. Compass X, Integer, 0 if N/A
1. Compass Y, Integer, 0 if N/A
1. Compass Z, Integer, 0 if N/A
1. Flex sensor A, Integer, ring finger by default
1. Flex sensor B, Integer, middle finger by default
1. Flex sensor C, Integer, index finger by default
1. Flex sensor D, Integer, thumb by default
1. Flex sensor E, Integer, not implemented/0 by default
1. Touch sensor A, 0 or 1, not implemented/0 by default
1. Touch sensor B, 0 or 1, pinky by default
1. Touch sensor C, 0 or 1, ring finger by default
1. Touch sensor D, 0 or 1, middle finger by default
1. Touch sensor E, 0 or 1, index finger by default
1. Touch sensor F, 0 or 1, index swipe left by default
1. Touch sensor G, 0 or 1, index swipe right by default
1. Touch sensor H, 0 or 1, not implemented/0 by default
1. Undefined packet for extension

## Example packet:

```
-4168 596 15352 -1110 -182 -71 0 0 0 541 697 543 602 597 1 1 1 1 1 1 1 0 164
```