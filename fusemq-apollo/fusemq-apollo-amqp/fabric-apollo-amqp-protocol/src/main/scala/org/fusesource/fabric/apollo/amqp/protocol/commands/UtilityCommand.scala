/*
 * Copyright (C) FuseSource, Inc.
 * http://fusesource.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.fusesource.fabric.apollo.amqp.protocol.commands

/**
 *
 */

class UtilityCommand extends Command

object ReleaseChain {
  val INSTANCE = new ReleaseChain
  def apply() = INSTANCE
}
class ReleaseChain extends UtilityCommand

object ChainReleased {
  val INSTANCE = new ChainReleased
  def apply() = INSTANCE
}
class ChainReleased extends UtilityCommand

object ChainAttached {
  val INSTANCE = new ChainAttached
  def apply() = INSTANCE
}
class ChainAttached extends UtilityCommand

